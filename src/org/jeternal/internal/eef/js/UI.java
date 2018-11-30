package org.jeternal.internal.eef.js;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jeternal.internal.Jeternal;
import org.jeternal.sdk.components.Window;

public class UI {

	public static class ImageView extends JPanel {
		
		private Image image;
		
		public void setImage(Image image) {
			this.image = image;
		}
		
		public Image getImage() {
			return image;
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			if (image != null) {
				g.drawImage(image, 0, 0, null);
			}
		}
	}
	
	public Window createWindow() {
		Window window = new Window();
		Jeternal.desktop.add(window);
		return window;
	}
	
	public Object createComponent(String name, Object... args) {
		System.out.println("create " + name);
		if (name.equals("Button")) {
			return new JButton("{jeternal.default.button}");
		}
		if (name.equals("ImageView")) {
			return new ImageView();
		}
		if (name.equals("Label")) {
			return new JLabel("{jeternal.default.label}");
		}
		if (name.equals("Pane")) {
			return new JPanel();
		}
		if (name.equals("Scroller")) {
			return new JScrollPane((JComponent) args[0]);
		}
		return null;
	}
	
	public Image readImage(String file) {
		try {
			return ImageIO.read(new File(file));
		} catch (IOException e) {
			return null;
		}
	}
	
}
