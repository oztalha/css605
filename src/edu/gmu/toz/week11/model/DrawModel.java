package edu.gmu.toz.week11.model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import edu.gmu.toz.week11.model.event.DrawEvent;
import edu.gmu.toz.week11.model.event.DrawListener;

/**
 * Model for storing everything that should be drawn.
 * 
 * @author randy
 *
 */
public class DrawModel implements Drawable {

	private EventListenerList drawListenerList = new EventListenerList();

	private List<Drawable> drawableList = new ArrayList<Drawable>();

	public List<Drawable> getDrawableList(){
		return drawableList;
	}
	
	public void addDrawable(Drawable newDrawable) {
		drawableList.add(newDrawable);

		// Fire an event so any views will know to update
		fireDrawEvent(newDrawable);
	}

	@Override
	public void draw(Graphics g) {
		for (Drawable drawable : drawableList) {
			drawable.draw(g);
		}
	}

	public void addDrawListener(DrawListener l) {
		drawListenerList.add(DrawListener.class, l);
	}

	public void removeDrawListener(DrawListener l) {
		drawListenerList.remove(DrawListener.class, l);
	}

	protected void fireDrawEvent(Drawable newDrawable) {
		// Adapted from Java API:
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/event/EventListenerList.html
		// Guaranteed to return a non-null array
		Object[] listeners = drawListenerList.getListenerList();
		DrawEvent drawEvent = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DrawListener.class) {
				// Lazily create the event:
				if (drawEvent == null) {
					drawEvent = new DrawEvent(this, newDrawable);
				}
				((DrawListener) listeners[i + 1]).objectDrawn(drawEvent);
			}
		}
	}

	@Override
	public boolean contains(Point p) {
		return false;
	}
	
	public void removeDrawables(List<Drawable> toBeRemoved){
		drawableList.removeAll(toBeRemoved);
		// Since there is no new object drawn, then just pass in null
		fireDrawEvent(null);
	}

}
