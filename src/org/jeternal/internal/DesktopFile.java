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
		this.file = file;
		box = new JCheckBox();
		MouseAdapter adapter = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2)
						Jeternal.shell(file);
					else if (e.getClickCount() == 1)
						selected = true;
				}
			}

			public void mouseEntered(MouseEvent e) {
				selectChance = true;
			}

			public void mouseExited(MouseEvent e) {
				selectChance = false;
				selected = false;
			}

		};
		addMouseListener(adapter);
	}

	public void paint(Graphics g) {
		box.setSelected(selected);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawString(file.getName(), getWidth() / 2 - g.getFontMetrics().stringWidth(file.getName()) / 2,
				getHeight() / 2 - g.getFontMetrics().getHeight());
		if (selectChance == true) {
			g.setColor(Color.ORANGE);
			g.fillRect(4, 4, getWidth() / 4, getHeight() / 4);
			g.setColor(Color.BLACK);
			g.drawRect(4, 4, getWidth() / 4, getHeight() / 4);
		}
		if (selected == true) {
			if (selectChance == true)
				selectChance = false;
			g.translate(3, 12);
			box.setLocation(0, 4);
			box.setSize(4, 4);
			box.paint(g);
			g.translate(-3, -12);
		}
	}

}
