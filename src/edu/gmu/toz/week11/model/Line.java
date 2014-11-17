package edu.gmu.toz.week11.model;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A Line Drawable.
 * 
 * @author randy
 *
 */
public class Line implements Drawable {

	private Point location1, location2;

	public Line(Point location1, Point location2) {
		this.location1 = location1;
		this.location2 = location2;
	}

	public Point getStartLocation() {
		return location1;
	}

	public Point getEndLocation() {
		return location2;
	}

	@Override
	public void draw(Graphics g) {
		g.drawLine(location1.x, location1.y, location2.x, location2.y);
	}
	
	@Override
	public boolean contains(Point p) {

		Point upperLeft = location1;
		Point lowerRight = location2;

		double lineDist = upperLeft.distance(lowerRight);
		double distThroughPoint = upperLeft.distance(p) + p.distance(lowerRight);
		
		return distThroughPoint - lineDist < 2.0;
	}
}
