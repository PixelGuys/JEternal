package org.jeternal.internal;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jeternal.sdk.FileSystem;

public class FSStatusScreen extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String text;
	private Image image;
	
	public FSStatusScreen(String text) {
		try {
			this.text = text;
			image = ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/JEternalLogo128.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(
				image,
				getWidth() / 2 - 64,
				getHeight() / 2 - 64,
				null);
		g.drawString(
				text, 
				getWidth() / 2 + 18 - g.getFontMetrics().stringWidth(text)/2,
				getHeight() / 2 + 80);
	}

}
