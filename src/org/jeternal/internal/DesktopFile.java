package org.jeternal.internal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jeternal.sdk.components.Cursors;

public class DesktopFile extends JComponent {

	private File file;
	private boolean selectChance;
	private boolean selected;
	private JCheckBox box;

	public boolean isSelected() {
		return selected;
	}

	public DesktopFile(File file) {
		box = new JCheckBox();
		box.setSize(12, 12);
		box.setVisible(false);
		box.setBorderPaintedFlat(true);
		setLayout(null);
		add(box);
		box.setLocation(4, 12);
		this.file = file;
		MouseAdapter adapter = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2)
						Jeternal.shell(file);
					else if (e.getClickCount() == 1)
						selected = true;
				}
				repaint();
			}

			public void mouseEntered(MouseEvent e) {
				selectChance = true;
				box.setVisible(true);
				repaint();
			}

			public void mouseExited(MouseEvent e) {
				selectChance = false;
				selected = false;
				box.setVisible(false);
				repaint();
			}

		};
		addMouseListener(adapter);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawString(file.getName(), getWidth() / 2 - g.getFontMetrics().stringWidth(file.getName()) / 2,
				getHeight() / 2 - g.getFontMetrics().getHeight());
		if (selectChance == true) {
			box.setSelected(false);
		}
		if (selected == true) {
			if (selectChance == true)
				selectChance = false;
			box.setSelected(true);
		}
	}

}
