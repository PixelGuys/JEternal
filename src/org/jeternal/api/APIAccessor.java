package org.jeternal.api;

import org.jeternal.internal.Jeternal;

public class APIAccessor {

	private static DesktopAPI desktop;
	private static SystemAPI system;
	
	public static DesktopAPI getDesktop() {
		if (Jeternal.desktop == null) {
			throw new IllegalStateException("Desktop not initialized");
		}
		if (desktop == null && Jeternal.desktop != null) {
			desktop = new DesktopAPI(Jeternal.desktop);
		}
		return desktop;
	}
	
	public static SystemAPI getSystem() {
		if (system == null) {
			system = new SystemAPI();
		}
		return system;
	}
	
}
