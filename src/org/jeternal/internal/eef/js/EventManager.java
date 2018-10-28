package org.jeternal.internal.eef.js;

import java.util.ArrayList;

public class EventManager {

	private static ArrayList<Event> eventQueue = new ArrayList<Event>();
	
	public Event pullEvent() {
		while (eventQueue.isEmpty()) {
			Thread.onSpinWait();
		}
		Event evt = eventQueue.get(eventQueue.size() - 1);
		eventQueue.remove(evt);
		return evt;
	}
	
	public static synchronized void registerEvent(Event evt) {
		if (eventQueue.size() > 0) {
			eventQueue.add(0, evt);
		} else {
			eventQueue.add(evt); // Bug Fix #948
		}
	}
	
}
