package org.jeternal.internal.eef.js;

public abstract class Event {

	public Object[] bundled = new Object[0];
	public Object source;
	public String type;
	
	public Event(Object[] bundled, Object source, String type) {
		this.bundled = bundled;
		this.source = source;
		this.type = type;
	}
	
	public abstract void accept();
	
}
