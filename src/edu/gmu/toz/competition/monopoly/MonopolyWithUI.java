/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package edu.gmu.toz.competition.monopoly;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jfree.data.xy.XYSeries;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.portrayal.simple.ShapePortrayal2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.media.chart.TimeSeriesAttributes;
import sim.util.media.chart.TimeSeriesChartGenerator;
import edu.gmu.toz.competition.monopoly.Monopoly.SpaceRecord;

public class MonopolyWithUI extends GUIState {

	// Colors are red, blue, green, and purple (from www.colorbrewer2.org)
	public static final Color[] PLAYER_COLORS = new Color[] {
			new Color(228, 26, 28), new Color(55, 126, 184),
			new Color(77, 175, 74), new Color(152, 78, 163) };

	public static final Stroke thinLine = new BasicStroke(1);

	public static final Stroke thickLine = new BasicStroke(4);

	public Display2D display;
	public JFrame displayFrame;

	ContinuousPortrayal2D gameLogoPortrayal = new ContinuousPortrayal2D() {
		public void hitObjects(DrawInfo2D range, Bag putInHere) {
			// There is no reason to select the game logo, so just return
			return;
		}
	};

	ObjectGridPortrayal2D gameBoardPortrayal = new ObjectGridPortrayal2D();

	SparseGridPortrayal2D playerLocationsPortrayal = new SparseGridPortrayal2D();

	TimeSeriesChartGenerator chartGenerator;

	Map<MonopolyPlayer, XYSeries> playerToMoneySeries = new HashMap<MonopolyPlayer, XYSeries>();

	Map<MonopolyPlayer, XYSeries> playerToWealthSeries = new HashMap<MonopolyPlayer, XYSeries>();

	Map<MonopolyPlayer, Color> playerToColorMap = new HashMap<MonopolyPlayer, Color>();

	public static void main(String[] args) {
		new MonopolyWithUI().createController();
	}

	public MonopolyWithUI() {
		super(new Monopoly(System.currentTimeMillis()));
	}

	public MonopolyWithUI(SimState state) {
		super(state);
	}

	public static String getName() {
		return "Monopoly";
	}

	public void start() {
		super.start();

		// Set up the colors of the players
		setupPlayerColors();

		// Set up our portrayals
		setupPortrayals();

		// Set up time series graph
		setupGraphs();
	}

	public void load(SimState state) {
		super.load(state);
		// we now have new grids. Set up the portrayals to reflect that
		setupPortrayals();
	}

	public void setupPlayerColors() {

		playerToColorMap.clear();

		Monopoly game = (Monopoly) state;

		int playerCount = 0;
		for (MonopolyPlayer p : game.getPlayers()) {
			playerToColorMap.put(p, PLAYER_COLORS[playerCount
					% PLAYER_COLORS.length]);
			playerCount++;
		}
	}

	// This is called by start() and by load() because they both had this code
	// so I didn't have to type it twice :-)
	public void setupPortrayals() {

		// Setting up the field and portrayal for the logo on the game board
		Continuous2D gameLogo = new Continuous2D(11, 11, 11);
		gameLogo.setObjectLocation("object", new Double2D(5.5, 5.5));
		gameLogoPortrayal.setField(gameLogo);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(
				"605opoly_logo.gif"));
		Image image = imageIcon.getImage();
		int dimension = (int) Math.min(gameLogo.width, gameLogo.height);
		gameLogoPortrayal.setPortrayalForAll(new ImagePortrayal2D(image,
				dimension));

		// Tell the portrayals what to portray and how to portray them
		// This portrayal displays the spaces on the game board
		gameBoardPortrayal.setField(((Monopoly) state).gameBoardGrid);

		Font font = new Font("SansSerif", Font.BOLD, 12);

		for (Space space : Space.values()) {

			SpaceRecord spaceRecord = ((Monopoly) state).getSpaceRecord(space);

			if (space.equals(Space.GO)) {
				gameBoardPortrayal.setPortrayalForObject(spaceRecord,
						new LabelledPortrayal2D(new SpacePortrayal2D(
								spaceRecord), 0, 5, 0, 0, font, 0, "GO",
								Color.BLUE, false));
			} else {
				gameBoardPortrayal.setPortrayalForObject(spaceRecord,
						new SpacePortrayal2D(spaceRecord));
			}
		}

		// This portrayal display the locations of the players
		playerLocationsPortrayal
				.setField(((Monopoly) state).playerLocationGrid);

		int playerCount = 0;

		double[] xLocs = new double[] { -.25, .25, -.25, .25 };
		double[] yLocs = new double[] { -.25, -.25, .25, .25 };

		double halfShapeWidth = .16;
		double halfShapeHeight = .19;

		for (MonopolyPlayer player : ((Monopoly) state).getPlayers()) {
			double x = xLocs[playerCount % xLocs.length];
			double y = yLocs[playerCount % yLocs.length];

			playerLocationsPortrayal.setPortrayalForObject(
					player,
					new LabelledPortrayal2D(new ShapePortrayal2D(new double[] {
							x - halfShapeWidth, x - halfShapeWidth,
							x + halfShapeWidth, x + halfShapeWidth },
							new double[] { y + halfShapeHeight,
									y - halfShapeHeight, y - halfShapeHeight,
									y + halfShapeHeight }, playerToColorMap
									.get(player)), 0, 5, x, y, font, 0, ""
							+ (playerCount + 1), Color.BLACK, false));

			playerCount++;
		}

		// reschedule the displayer
		display.reset();

		// redraw the display
		display.repaint();
	}

	public void setupGraphs() {
		// Set up wealth chart
		Monopoly monopoly = (Monopoly) state;

		chartGenerator.removeAllSeries();
		playerToMoneySeries.clear();
		playerToWealthSeries.clear();

		int playerCount = 0;
		for (MonopolyPlayer player : monopoly.getPlayers()) {

			XYSeries playerMoneySeries = new XYSeries("Player "
					+ (playerCount + 1) + " Money");

			XYSeries playerWealthSeries = new XYSeries("Player "
					+ (playerCount + 1) + " Total Wealth");

			TimeSeriesAttributes playerMoneySeriesAttributes = (TimeSeriesAttributes) chartGenerator
					.addSeries(playerMoneySeries, null);
			TimeSeriesAttributes playerWealthSeriesAttributes = (TimeSeriesAttributes) chartGenerator
					.addSeries(playerWealthSeries, null);

			playerMoneySeriesAttributes.setStrokeColor(playerToColorMap
					.get(player));
			playerWealthSeriesAttributes.setStrokeColor(playerToColorMap
					.get(player));

			playerToMoneySeries.put(player, playerMoneySeries);
			playerToWealthSeries.put(player, playerWealthSeries);

			playerCount++;
		}

		Steppable chartUpdater = new Steppable() {

			private static final long serialVersionUID = 1L;

			public void step(SimState state) {
				Monopoly monopoly = (Monopoly) state;

				for (MonopolyPlayer player : monopoly.getPlayers()) {
					XYSeries playerMoneySeries = playerToMoneySeries
							.get(player);
					XYSeries playerWealthSeries = playerToWealthSeries
							.get(player);

					if (playerMoneySeries != null) {
						playerMoneySeries.add(state.schedule.getSteps(),
								monopoly.getMoney(player));
					}
					if (playerWealthSeries != null) {
						playerWealthSeries.add(state.schedule.getSteps(),
								monopoly.getEntireWealth(player));
					}
				}

				// When a player goes bankrupt then display there wealth for
				// only one more step
				for (MonopolyPlayer bankruptPlayer : monopoly
						.getPlayersInBankruptcy()) {
					XYSeries playerMoneySeries = playerToMoneySeries
							.get(bankruptPlayer);
					XYSeries playerWealthSeries = playerToWealthSeries
							.get(bankruptPlayer);

					playerToMoneySeries.remove(bankruptPlayer);
					playerToWealthSeries.remove(bankruptPlayer);

					if (playerMoneySeries != null) {
						playerMoneySeries.add(state.schedule.getSteps(),
								monopoly.getMoney(bankruptPlayer));
					}
					if (playerWealthSeries != null) {
						playerWealthSeries.add(state.schedule.getSteps(),
								monopoly.getEntireWealth(bankruptPlayer));
					}
				}
			}
		};

		this.scheduleRepeatingImmediatelyAfter(chartUpdater);
	}

	public void init(Controller c) {
		super.init(c);

		// Make the Display2D. We'll have it display stuff later.
		display = new Display2D(400, 400, this); // at 400x400, we've got 4x4
													// per array position
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame); // register the frame so it appears in
										// the "Display" list
		displayFrame.setVisible(true);

		// attach the portrayals
		display.attach(gameLogoPortrayal, "Game Board Logo");
		display.attach(gameBoardPortrayal, "Game Board");
		display.attach(playerLocationsPortrayal, "Game Players");

		// Init linear chart
		chartGenerator = new TimeSeriesChartGenerator();
		chartGenerator.setTitle("Monopoly Players Wealth");

		chartGenerator.setXAxisLabel("Time");
		chartGenerator.setYAxisLabel("Wealth");

		JFrame chartGeneratorFrame = chartGenerator.createFrame();
		chartGeneratorFrame.setVisible(true);

		c.registerFrame(chartGeneratorFrame);

		// specify the backdrop color -- what gets painted behind the displays
		display.setBackdrop(Color.WHITE);
	}

	public void quit() {
		super.quit();

		if (displayFrame != null)
			displayFrame.dispose();
		displayFrame = null; // let gc
		display = null; // let gc
	}

	public class SpacePortrayal2D extends RectanglePortrayal2D {

		private static final long serialVersionUID = 1L;

		private SpaceRecord spaceRecord;

		public SpacePortrayal2D(SpaceRecord spaceRecord) {
			super(spaceRecord.getSpace().getColor());

			this.spaceRecord = spaceRecord;
		}

		@Override
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			super.draw(object, graphics, info);

			Rectangle2D.Double draw = info.draw;
			final double width = draw.width * scale;
			final double height = draw.height * scale;

			final int x = (int) (draw.x - width / 2.0);
			final int y = (int) (draw.y - height / 2.0);
			final int w = (int) (width);
			final int h = (int) (height);

			if (spaceRecord.getOwner() == null) {
				graphics.setPaint(Color.BLACK);
			} else {
				graphics.setPaint(playerToColorMap.get(spaceRecord.getOwner()));
				graphics.setStroke(thickLine);
				graphics.drawLine(x, y, x + w, y + h);
				graphics.drawLine(x + w, y, x, y + h);
			}
			graphics.setStroke(thinLine);
			graphics.drawRect(x, y, w, h);

			// If the space has houses or hotels, then draw them
			if (spaceRecord.getNumImprovements() > 0) {
				Space space = spaceRecord.getSpace();

				if (spaceRecord.getNumImprovements() <= 4) {
					int houseWidth = w / 7;
					int houseBetweenSpace = w / 10;
					int houseX = x + houseBetweenSpace;
					int houseY = y - houseWidth - houseBetweenSpace;

					// On left side
					if (space.getXLoc() == 0) {
						houseX = x + w + houseBetweenSpace;
						houseY = y + houseBetweenSpace;
					}
					// On top side
					else if (space.getYLoc() == 0) {
						houseX = x + w - houseWidth - houseBetweenSpace;
						houseY = y + h + houseBetweenSpace;
					}
					// On right side
					else if (space.getXLoc() == 10) {
						houseX = x - houseWidth - houseBetweenSpace;
						houseY = y + h - houseWidth - houseBetweenSpace;
					}

					for (int i = 0; i < spaceRecord.getNumImprovements(); i++) {
						graphics.setPaint(Color.GREEN);
						graphics.fillRect(houseX, houseY, houseWidth,
								houseWidth);
						graphics.setPaint(Color.BLACK);
						graphics.drawRect(houseX, houseY, houseWidth,
								houseWidth);

						// On bottom side
						if (space.getYLoc() == 10) {
							houseX += houseWidth + houseBetweenSpace;
						}
						// On left side
						else if (space.getXLoc() == 0) {
							houseY += houseWidth + houseBetweenSpace;
						}
						// On top side
						else if (space.getYLoc() == 0) {
							houseX -= (houseWidth + houseBetweenSpace);
						}
						// On right side
						else if (space.getXLoc() == 10) {
							houseY -= (houseWidth + houseBetweenSpace);
						}
					}
				} else if (spaceRecord.getNumImprovements() == 5) {
					int hotelWidth = w / 3;
					int hotelHeight = w / 7;
					int hotelBetweenSpace = w / 10;

					int hotelX;
					int hotelY;

					// On bottom side
					if (space.getYLoc() == 10) {
						hotelX = x + hotelWidth;
						hotelY = y - hotelHeight - hotelBetweenSpace;

						graphics.setPaint(Color.RED);
						graphics.fillRect(hotelX, hotelY, hotelWidth,
								hotelHeight);
						graphics.setPaint(Color.BLACK);
						graphics.drawRect(hotelX, hotelY, hotelWidth,
								hotelHeight);
					}
					// On left side
					if (space.getXLoc() == 0) {
						hotelX = x + w + hotelBetweenSpace;
						hotelY = y + hotelWidth;

						graphics.setPaint(Color.RED);
						graphics.fillRect(hotelX, hotelY, hotelHeight,
								hotelWidth);
						graphics.setPaint(Color.BLACK);
						graphics.drawRect(hotelX, hotelY, hotelHeight,
								hotelWidth);
					}
					// On top side
					else if (space.getYLoc() == 0) {
						hotelX = x + w - hotelWidth * 2;
						hotelY = y + h + hotelBetweenSpace;

						graphics.setPaint(Color.RED);
						graphics.fillRect(hotelX, hotelY, hotelWidth,
								hotelHeight);
						graphics.setPaint(Color.BLACK);
						graphics.drawRect(hotelX, hotelY, hotelWidth,
								hotelHeight);
					}
					// On right side
					else if (space.getXLoc() == 10) {
						hotelX = x - hotelHeight - hotelBetweenSpace;
						hotelY = y + h - hotelWidth * 2;

						graphics.setPaint(Color.RED);
						graphics.fillRect(hotelX, hotelY, hotelHeight,
								hotelWidth);
						graphics.setPaint(Color.BLACK);
						graphics.drawRect(hotelX, hotelY, hotelHeight,
								hotelWidth);
					}
				}
			}

			// graphics.drawRect(x, y-5,w,h);
		}
	}
}
