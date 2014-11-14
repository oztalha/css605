package edu.gmu.toz.week11.model.event;

import java.util.EventObject;

import edu.gmu.toz.week11.model.Drawable;

/**
 * When a Drawable is add to the model, this type of event is generated.
 * 
 * @author randy
 *
 */
public class DrawEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Drawable drawable;

	public DrawEvent(Object source, Drawable drawable) {
		super(source);

		this.drawable = drawable;
	}

	public Drawable getDrawable() {
		return drawable;
	}
}
