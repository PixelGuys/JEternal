package org.jeternal.api;

import org.jeternal.internal.*;

public class SystemAPI {

	public void forceShutdown() {
		Jeternal.shutdown();
	}
}