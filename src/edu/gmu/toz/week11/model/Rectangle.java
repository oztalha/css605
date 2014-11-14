package edu.gmu.toz.week11.model;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A Rectangle Drawable.
 * 
 * @author randy
 *
 */
public class Rectangle extends AbstractShape {

	private int width;

	private int height;

	public Rectangle(Point location, int width, int height) {
		super(location);
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(getLocation().x, getLocation().y, width, height);
	}
}