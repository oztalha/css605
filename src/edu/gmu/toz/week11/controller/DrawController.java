package edu.gmu.toz.week11.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import edu.gmu.toz.week11.model.DrawModel;
import edu.gmu.toz.week11.model.Drawable;
import edu.gmu.toz.week11.model.Line;
import edu.gmu.toz.week11.model.Oval;
import edu.gmu.toz.week11.model.Rectangle;

/**
 * Controller for creating new Drawables.
 * 
 * @author randy
 *
 */
public class DrawController implements MouseListener {

	private DrawModel drawModel;

	private DrawMode drawMode = DrawMode.RECTANGLE;

	private Point previousPoint;

	public DrawController(DrawModel drawModel) {
		this.drawModel = drawModel;
	}

	public void setDrawMode(DrawMode drawMode) {
		this.drawMode = drawMode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (drawMode.equals(DrawMode.ERASE)) {
			List<Drawable> toBeRemoved = new ArrayList<Drawable>();
			Point curPoint = e.getPoint();
			toBeRemoved = drawModel.getDrawableList();
			drawModel.removeDrawables(toBeRemoved);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		previousPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (previousPoint != null) {
			Point curPoint = e.getPoint();

			Drawable newDrawable = null;

			int width = Math.abs(curPoint.x - previousPoint.x);
			int height = Math.abs(curPoint.y - previousPoint.y);

			if (drawMode.equals(DrawMode.RECTANGLE)) {
				Point topleft = new Point(previousPoint);
				//always top-left of the rectangle should be passed 
				if(previousPoint.x>curPoint.x && previousPoint.y>curPoint.y)
					topleft.setLocation(curPoint.x, curPoint.y);
				if(curPoint.x>previousPoint.x && previousPoint.y>curPoint.y)
					topleft.setLocation(previousPoint.x, curPoint.y);
				if(previousPoint.x>curPoint.x && curPoint.y>previousPoint.y)
					topleft.setLocation(curPoint.x, previousPoint.y);		
				//instead of previousPoint now we send topleft point
				newDrawable = new Rectangle(topleft, width, height);

			} else if (drawMode.equals(DrawMode.OVAL)) {

				newDrawable = new Oval(previousPoint, width, height);

			} else if (drawMode.equals(DrawMode.LINE)) {

				newDrawable = new Line(previousPoint, curPoint);
			}

			previousPoint = null;

			if (newDrawable != null) {
				drawModel.addDrawable(newDrawable);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing for now...

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing for now...

	}
}
