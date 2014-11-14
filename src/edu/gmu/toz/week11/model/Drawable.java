package edu.gmu.toz.week11.model;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Simple interface for drawing things.
 * 
 * @author randy
 *
 */
public interface Drawable {

	public void draw(Graphics g);
	public boolean contains(Point p);
	
}
