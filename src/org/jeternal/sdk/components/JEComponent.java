package org.jeternal.sdk.components;

import java.awt.image.*;

import javax.swing.*;

public class JEComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage cursor;
	
	public void setCursor(BufferedImage cursor) {
		this.cursor = cursor;
	}
	
	public BufferedImage getLightWeightCursor() {
		return cursor;
	}
	
}
