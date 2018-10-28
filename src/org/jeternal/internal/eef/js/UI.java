package org.jeternal.internal.eef.js;

import javax.swing.JButton;

import org.jeternal.internal.Jeternal;
import org.jeternal.sdk.components.Window;

public class UI {

	public Window createWindow() {
		Window window = new Window();
		Jeternal.desktop.add(window);
		return window;
	}
	
	public Object createComponent(String name) {
		System.out.println("create " + name);
		if (name.equals("Button")) {
			return new JButton("Test");
		}
		return null;
	}
	
}
