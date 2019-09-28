package org.jeternal.internal;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class DesktopFile extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private File file;
	private boolean selectChance;
	private boolean selected;
	private JCheckBox box;
	public Image icon;
	private JPopupMenu popup;

	public boolean isSelected() {
		return selected;
	}
	
	public DesktopFile(File file) {
		box = new JCheckBox();
		box.setSize(12, 12);
		box.setVisible(false);
		box.setBorderPaintedFlat(true);
		box.setFocusPainted(false);
		box.setIconTextGap(0);
		box.setToolTipText("yosh");
		setLayout(null);
		setOpaque(false);
		add(box);
		box.setLocation(4, 12);
		this.file = file;
		
		popup = new JPopupMenu();
		JMenuItem open = new JMenuItem("Open");
		JMenuItem props = new JMenuItem("Properties");
		popup.add(open);
		popup.addSeparator();
		popup.add(props);
		MouseAdapter adapter = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					if (e.getClickCount() == 2)
						try {
							Jeternal.shell(file);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				if (e.getClickCount() == 1) {
					selected = true;
					repaint();
					return;
				}
				if (e.getButton() == MouseEvent.BUTTON3 && selected)
					popup.show(DesktopFile.this, e.getX(), e.getY());
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
		//this.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.black));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(icon, 0, 0, getWidth(), getHeight(), null);
		if (selectChance == true)
			box.setSelected(false);
		if (selectChance || selected) {
			g.setColor(new Color(127, 255, 255, 127));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (selected == true) {
			if (selectChance == true)
				selectChance = false;
			box.setSelected(true);
		}
		g.setColor(Color.BLACK);
		g.drawString(file.getName(), getWidth() / 2 - g.getFontMetrics().stringWidth(file.getName()) / 2, getHeight() - g.getFontMetrics().getHeight());
	}
}