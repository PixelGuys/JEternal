package org.jeternal.internal.eef.js;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
import org.jeternal.internal.*;
import org.jeternal.sdk.components.Window;

public class UI {

	public static class ImageView extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private Image image;

		public void setImage(Image image) {
			this.image = image;
		}

		public Image getImage() {
			return image;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			if (image != null) {
				g.drawImage(image, 0, 0, null);
			}
		}
	}

	public static class ChoiceBox extends JComboBox<Object> {

		private static final long serialVersionUID = 1L;
		
		private Object[] o;

		public void setValues(Object[] values) {
			o = values;
			revalidate();
			repaint();
		}

		public ChoiceBox() {
			setModel(new DefaultComboBoxModel<Object>() {

				private static final long serialVersionUID = 1L;

				@Override
				public int getSize() {
					return o.length;
				}

				@Override
				public Object getElementAt(int index) {
					return o[index];
				}

			});
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

	int fsm;

	public int getFullscreenMode() {
		return fsm;
	}

	/**
	 * 0 = windowed,
	 * 1 = borderless,
	 * 2 = exclusive
	 * @param i
	 */
	public void setFullscreenMode(int i, DisplayMode req) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		if (i == 0 || i == 1) {
			gd.setFullScreenWindow(null);
			if (i == 1) {
				Jeternal.jEternal.dispose();
				Jeternal.jEternal.setUndecorated(true);
				Jeternal.jEternal.setVisible(true);
				Jeternal.jEternal.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else {
				if (Jeternal.jEternal.isUndecorated()) {
					Jeternal.jEternal.dispose();
					Jeternal.jEternal.setUndecorated(false);
					Jeternal.jEternal.setVisible(true);
					Jeternal.jEternal.setExtendedState(JFrame.NORMAL);
				}
			}
		}
		if (i != 1) {
			if (fsm == 1) {
				Jeternal.jEternal.setExtendedState(JFrame.NORMAL);
				Jeternal.jEternal.dispose();
				Jeternal.jEternal.setUndecorated(false);
				Jeternal.jEternal.setVisible(true);
			}
		}
		if (i == 2) {
			gd.setFullScreenWindow(Jeternal.jEternal);
			if (req != null) {
				gd.setDisplayMode(req);
			}
		}
		fsm = i;
	}

	public DisplayMode[] getDisplayModes() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayModes();
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
		if (name.equals("Button"))
			return new JButton("{jeternal.default.button}");
		if (name.equals("ImageView"))
			return new ImageView();
		if (name.equals("Label"))
			return new JLabel("{jeternal.default.label}");
		if (name.equals("Pane"))
			return new JPanel();
		if (name.equals("Scroller"))
			return new JScrollPane((JComponent) args[0]);
		if (name.equals("PasswordField"))
			return new JPasswordField();
		if (name.equals("TextField"))
			return new JTextField();
		if (name.equals("TextArea"))
			return new JTextArea();
		// Menus
		if (name.equals("MenuBar"))
			return new JMenuBar();
		if (name.equals("Menu"))
			return new JMenu();
		if (name.equals("MenuItem"))
			return new JMenuItem();
		if (name.equals("FileChooser"))
			return new JFileChooser();
		if (name.equals("ChoiceBox"))
			return new ChoiceBox();
		return null;
	}

	public Image readImage(String file) {
		try {
			return ImageIO.read(new File(file));
		} catch (Exception e) {
			return null;
		}
	}
}