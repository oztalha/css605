package edu.gmu.toz.week11;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.gmu.toz.week11.controller.DrawController;
import edu.gmu.toz.week11.model.DrawModel;
import edu.gmu.toz.week11.view.DrawPanel;

/**
 * The starting point for the drawing application (contains the main methods).
 * 
 * @author randy
 *
 */
public class DrawApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	// Model
	private DrawModel drawModel;

	// View
	private DrawPanel drawPanel;

	// Controller
	private DrawController drawController;

	public DrawApplication() {
		super("Drawing Application");
		getContentPane().add(getDrawPanel());
		setPreferredSize(new Dimension(500, 400));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public DrawPanel getDrawPanel() {
		if (drawPanel == null) {
			drawPanel = new DrawPanel(getDrawController(), getDrawModel());
		}

		return drawPanel;
	}

	public DrawController getDrawController() {
		if (drawController == null) {
			drawController = new DrawController(getDrawModel());
		}

		return drawController;
	}

	public DrawModel getDrawModel() {
		if (drawModel == null) {
			drawModel = new DrawModel();
		}

		return drawModel;
	}

	public static void main(String[] args) {
		JFrame frame = new DrawApplication();
		frame.setVisible(true);
	}
}
