package edu.gmu.toz.week11.model;

import java.awt.Graphics;
import java.awt.Point;

/**
 * An Oval Drawable.
 * 
 * @author randy
 *
 */
public class Oval extends Rectangle {

	public Oval(Point location, int width, int height) {
		super(location, width, height);
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(getLocation().x, getLocation().y, getWidth(), getHeight());
	}
}
