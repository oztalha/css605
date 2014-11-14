package edu.gmu.toz.week11.model;

import java.awt.Point;

/**
 * Abstract class for storing shape information. Only attribute is location
 * because to draw any shape, you will need to know where to draw it.
 * 
 * @author randy
 *
 */
public abstract class AbstractShape implements Shape, Drawable {

	private Point location;

	public AbstractShape(Point location) {
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}
}
