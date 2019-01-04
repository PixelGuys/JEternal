package org.jeternal.internal.eef.js;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class EventManager {

	static HashMap<String, LinkedBlockingDeque<Event>> eventQueue = new HashMap<>();
	
	public synchronized Event pullEvent(String id) {
		if (!eventQueue.containsKey(id)) {
			return null; // not registered
		}
		while (eventQueue.get(id).isEmpty()) {
			Thread.onSpinWait();
		}
		LinkedBlockingDeque<Event> evtQueue = eventQueue.get(id);
		Event evt;
		try {
			evt = evtQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		if (evt == null) {
			return pullEvent(id);
		}
		System.out.println("Took Event: " + evt + " for " + id);
		return evt;
	}
	
	public static synchronized void registerEvent(String id, Event evt) {
		eventQueue.get(id).add(evt);
	}
	
}
