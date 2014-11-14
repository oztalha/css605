package edu.gmu.toz.week11.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import edu.gmu.toz.week11.controller.DrawController;
import edu.gmu.toz.week11.controller.DrawMode;
import edu.gmu.toz.week11.model.DrawModel;

/**
 * Holder for the draw buttons and the DrawComponent (the canvas).
 * 
 * @author randy
 *
 */
public class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DrawController drawController;

	private DrawModel drawModel;

	private DrawComponent drawComponent;

	private JToolBar drawToolbar;

	public DrawPanel(DrawController drawController, DrawModel drawModel) {
		super(new BorderLayout());

		this.drawController = drawController;
		this.drawModel = drawModel;

		add(getDrawComponent(), BorderLayout.CENTER);
		add(getDrawToolbar(), BorderLayout.PAGE_START);
	}

	public DrawComponent getDrawComponent() {
		if (drawComponent == null) {
			drawComponent = new DrawComponent(drawModel);
			drawComponent.addMouseListener(drawController);
			drawModel.addDrawListener(drawComponent);
		}

		return drawComponent;
	}

	public JToolBar getDrawToolbar() {
		if (drawToolbar == null) {
			drawToolbar = new JToolBar();

			// Add the draw buttons to a button group so when one button
			// selected, any other selected button gets unselected.
			ButtonGroup buttonGroup = new ButtonGroup();

			// Add each type of item that could be drawn to the panel
			for (DrawMode drawMode : DrawMode.values()) {
				DrawModeAction drawModeAction = new DrawModeAction(drawMode);
				JToggleButton drawModeButton = new JToggleButton(drawModeAction);

				// Make the first button selected and set the mode in the
				// controller
				if (buttonGroup.getButtonCount() == 0) {
					drawModeButton.setSelected(true);
					drawController.setDrawMode(drawMode);
				}

				// Add the button to the group and toolbar
				buttonGroup.add(drawModeButton);
				drawToolbar.add(drawModeButton);
			}
		}

		return drawToolbar;
	}

	public class DrawModeAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private DrawMode drawMode;

		public DrawModeAction(DrawMode drawMode) {
			super(drawMode.toString());
			this.drawMode = drawMode;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			drawController.setDrawMode(drawMode);
		}
	}
}
