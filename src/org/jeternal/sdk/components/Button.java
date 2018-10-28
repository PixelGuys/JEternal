package org.jeternal.sdk.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class Button extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private Runnable onAction;
	private String text = "";
	private Image icon;
	
	public Button() {
		setBackground(new Color(158, 159, 157));
		setForeground(Color.BLACK);
		setCursor(Cursors.ACTION_CURSOR);
		MouseAdapter adapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (onAction != null)
					onAction.run();
			}
		};
		addMouseListener(adapter);
		setPreferredSize(new java.awt.Dimension(32, 32));
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setOnAction(Runnable run) {
		this.onAction = run;
	}
	
	public Runnable getOnAction() {
		return this.onAction;
	}
	
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		if (icon == null)
			g.drawString(text, getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, getHeight() / 2);
		else
			g.drawImage(icon, 4, 4, getWidth() - 8, getHeight() - 12, this);
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}
	
}
