package org.jeternal.internal.eef.js;

public class Misc {
	
	public Object loadModule(String pack, String name) {
		if (pack.equals("system")) {
			if (name.equals("ui")) {
				return new UI();
			}
			if (name.equals("event")) {
				return new EventManager();
			}
		}
		return null;
	}
	
	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
