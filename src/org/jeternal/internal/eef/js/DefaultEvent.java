package org.jeternal.internal.eef.js;

public class DefaultEvent extends Event {

	public DefaultEvent(Object[] bundled, Object source, String type) {
		super(bundled, source, type);
	}

	@Override
	public void accept() {
		
	}
	
	public String toString() {
		return "DefaultEvent[bundled=" + bundled + ",source=" + source.getClass() + ",type=" + type + "]";
	}

}
