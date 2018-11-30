package org.jeternal.sdk.components;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class JEComponent extends JComponent {

	private BufferedImage cursor;
	
	public void setCursor(BufferedImage cursor) {
		this.cursor = cursor;
	}
	
	public BufferedImage getLightWeightCursor() {
		return cursor;
	}
	
}
