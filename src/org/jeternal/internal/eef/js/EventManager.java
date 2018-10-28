package org.jeternal.internal.eef.js;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

	private static java.util.concurrent.CopyOnWriteArrayList<Event> eventQueue = new CopyOnWriteArrayList<Event>();
	
	public synchronized Event pullEvent() {
		while (eventQueue.isEmpty()) {
			Thread.onSpinWait();
		}
		Event[] eventQueueCopy = eventQueue.toArray(new Event[eventQueue.size()]);
		Event evt = eventQueueCopy[eventQueueCopy.length - 1];
		if (eventQueue.size() - 1 > 0) {
			try {
				eventQueue.remove(eventQueue.size() - 1);
			} catch (Exception e) {
				
			}
		}
		return evt;
	}
	
	public static synchronized void registerEvent(Event evt) {
		//eventQueue.trimToSize();
		if (eventQueue.size() > 0) {
			eventQueue.add(0, evt);
		} else {
			eventQueue.add(0, evt);
		}
		//eventQueue.trimToSize();
	}
	
}
