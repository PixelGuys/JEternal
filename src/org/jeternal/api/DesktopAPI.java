package org.jeternal.api;

import org.jeternal.internal.Desktop;
import org.jeternal.sdk.components.Window;

public class DesktopAPI {

	private Desktop desktop;
	
	DesktopAPI(Desktop desktop) {
		this.desktop = desktop;
	}
	
	public void refresh() {
		desktop.refreshDesktop();
	}
	
	public void addWindow(Window win) {
		desktop.add(win);
	}
	
	public void removeWindow(Window win) {
		desktop.remove(win);
	}
	
}
