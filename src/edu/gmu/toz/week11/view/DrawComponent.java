package edu.gmu.toz.week11.view;

import java.awt.Graphics;

import javax.swing.JComponent;

import edu.gmu.toz.week11.model.DrawModel;
import edu.gmu.toz.week11.model.event.DrawEvent;
import edu.gmu.toz.week11.model.event.DrawListener;

/**
 * The canvas for drawing the Drawables from the DrawModel.
 * 
 * @author randy
 *
 */
public class DrawComponent extends JComponent implements DrawListener {

	private static final long serialVersionUID = 1L;

	private DrawModel drawModel;

	public DrawComponent(DrawModel drawModel) {
		this.drawModel = drawModel;
	}

	protected void paintComponent(Graphics g) {
		drawModel.draw(g);
	}

	@Override
	public void objectDrawn(DrawEvent drawEvent) {
		repaint();
	}
}
