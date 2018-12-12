package org.jeternal.internal.eef.js;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

	//TODO use Collections.synchronizedList(...)
	private static ArrayDeque<Event> eventQueue = new ArrayDeque<Event>();
	
	public synchronized Event pullEvent() {
		while (eventQueue.isEmpty()) {
			Thread.onSpinWait();
		}
		Event evt = eventQueue.poll();
		if (evt == null) {
			return pullEvent();
		}
		return evt;
	}
	
	public static synchronized void registerEvent(Event evt) {
		eventQueue.add(evt);
	}
	
}
