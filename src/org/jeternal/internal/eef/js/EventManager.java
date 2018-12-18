package org.jeternal.internal.eef.js;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

public class EventManager {

	private static LinkedBlockingDeque<Event> eventQueue = new LinkedBlockingDeque<Event>();
	
	public synchronized Event pullEvent() {
		while (eventQueue.isEmpty()) {
			Thread.onSpinWait();
		}
		Event evt;
		try {
			evt = eventQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		if (evt == null) {
			return pullEvent();
		}
		return evt;
	}
	
	public static synchronized void registerEvent(Event evt) {
		eventQueue.add(evt);
	}
	
}
