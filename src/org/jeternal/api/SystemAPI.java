package org.jeternal.api;

import org.jeternal.internal.Jeternal;

public class SystemAPI {

	public void forceShutdown() {
		Jeternal.shutdown();
	}
	
}
