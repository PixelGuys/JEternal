package org.jeternal.internal.eef.js;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

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
	
	public Window createWindow(String appID) {
		Window window = new Window();
		window.setAppID(appID);
		Jeternal.desktop.add(window);
		return window;
	}
	
	public ActionListener toActionListener(Runnable run) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run.run();
			}
			
		};
	}
	
	public KeyStroke getKeyStroke(String ks) {
		return KeyStroke.getKeyStroke(ks);
	}
	
	public int getExtendedKeyCode(String s) {
		return KeyEvent.getExtendedKeyCodeForChar(s.toCharArray()[0]);
	}
	
	public int getExtendedKeyCodeChar(char ch) {
		return KeyEvent.getExtendedKeyCodeForChar(ch);
	}
	
	public Object createComponent(String name, Object... args) {
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
		if (name.equals("PasswordField")) {
			return new JPasswordField();
		}
		if (name.equals("TextField")) {
			return new JTextField();
		}
		if (name.equals("TextArea")) {
			return new JTextArea();
		}
		// Menus
		if (name.equals("MenuBar")) {
			return new JMenuBar();
		}
		if (name.equals("Menu")) {
			return new JMenu();
		}
		if (name.equals("MenuItem")) {
			return new JMenuItem();
		}
		if (name.equals("FileChooser")) {
			return new JFileChooser();
			
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
