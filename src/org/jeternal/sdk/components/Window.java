package org.jeternal.sdk.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent.Cause;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.jeternal.internal.Desktop;
import org.jeternal.internal.Jeternal;
import org.jeternal.internal.eef.js.DefaultEvent;
import org.jeternal.internal.eef.js.Event;
import org.jeternal.internal.eef.js.EventManager;

public class Window extends JInternalFrame {

	private Image icon;
	private Button windowButton;
	private boolean isIconDefined = false;
	private InternalFrameUI ui;
	private String appID;
	private boolean disposed;

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
		isIconDefined = true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Button getWindowButton() {
		return windowButton;
	}
	
	public void disposeWindow() {
		if (appID != null)
			EventManager.registerEvent(appID, new DefaultEvent(new Object[0], this, "dispose"));
		Desktop desktop = Jeternal.desktop;
		desktop.remove(this);
		System.gc();
		disposed = true;
	}
	
	boolean needRepaint = false;
	public boolean needsRepaint() {
		return needRepaint;
	}
	
	public void setAppID(String appId) {
		appID = appId;
	}

	public Window() {
		super("", true, true, true, true);
		//setLayout(null);
		//rootPane = new JRootPane();
		windowButton = new Button();
		icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		windowButton.setIcon(icon);
//		setFrameIcon(new Icon() {
//
//			@Override
//			public void paintIcon(Component c, Graphics g, int x, int y) {
//				g.drawImage(icon, x, y, c);
//			}
//
//			@Override
//			public int getIconWidth() {
//				return icon.getWidth(null);
//			}
//
//			@Override
//			public int getIconHeight() {
//				return icon.getHeight(null);
//			}
//			
//		});
		//setDesktopIcon(new JDesktopIcon(this));
		windowButton.setPreferredSize(new Dimension(94, windowButton.getPreferredSize().width));
		
		windowButton.setOnAction(new Runnable() {
			public void run() {
				try {
					if (Window.this.getFocusOwner() == null && !isIcon()) {
						Window.this.requestFocusInWindow(Cause.MOUSE_EVENT);
						return;
					}
					setIcon(!isIcon());
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});
		if (ui == null &&false ) ui = new BasicInternalFrameUI(this) {
			
			JPanel tp;
			private JLabel titleLabel;
			@Override
			public void paint(Graphics g, JComponent i) {
				if (getNorthPane() != tp) {
					setNorthPane(tp);
					//System.out.println("set to " + tp);
				}
				JInternalFrame f = (JInternalFrame) i;
				titleLabel.setText(f.getTitle());
				f.setVisible(!f.isIcon());
			}
			
			
			
			protected JComponent createNorthPane(JInternalFrame i) {
				titleLabel = new JLabel(i.getTitle());
				titleLabel.setForeground(Color.WHITE);
				JButton closeButton = new JButton("X");
				closeButton.addActionListener(event -> {
					i.getDesktopPane().getDesktopManager().closeFrame(i);
					disposeWindow();
				});
				JButton iconifyButton = new JButton("_");
				iconifyButton.addActionListener(event -> {
					try {
						i.setIcon(!i.isIcon());
					} catch (PropertyVetoException e) {
						
					}
				});
				closeButton.setBackground(Color.RED);
				tp = new JPanel() {
					
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						if (true) {
							g.setColor(new Color(1, 1, 1));
						} else {
							g.setColor(new Color(127, 127, 127));
						}
						g.fill3DRect(0, 0, getWidth(), 46, false);
					}
					
					{
						setLayout(new BorderLayout());
						add(BorderLayout.WEST, titleLabel);
						JPanel rightPanel = new JPanel();
						rightPanel.setBackground(Color.BLACK);
						rightPanel.add(iconifyButton);
						rightPanel.add(closeButton);
						add(BorderLayout.EAST, rightPanel);
					}
					
				};
				
				return tp;
			}
			
		};
		this.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				if (!disposed) {
					disposeWindow();
				}
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {}

			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				setVisible(false);
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				setVisible(true);
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent e) {}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {}
			
		});
		System.out.println(icon);
		//setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.BLACK));
		setSize(256, 256);
		//setMinimumSize(new Dimension(98, 46));
		setVisible(true);
		
		//setCursor(Cursors.DEFAULT_CURSOR);
	}
	
	public void setGraphicsLightweight(boolean lightweight) {
		this.lightweight = lightweight;
	}
	
	public Graphics getSoftwareGraphics() {
		return imgGraphics;
	}
	
	boolean lightweight = false;
	int increment = 0;
	BufferedImage oldImg;
	BufferedImage img = new BufferedImage(620, 480, BufferedImage.TYPE_4BYTE_ABGR);
	Graphics2D imgGraphics = img.createGraphics();
	private Dimension oldSize = new Dimension(0, 0);
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		windowButton.setText(getTitle());
		int titleHeight = this.getMinimumSize().height - this.getUI().getMinimumSize(this).height;
		if (ui != null && getUI() != ui) {
			setUI(ui);
		}
		if (getWidth() != img.getWidth() || getHeight() != img.getHeight() && lightweight) {
			oldImg = img;
			img = new BufferedImage(getWidth(), getHeight() - titleHeight, BufferedImage.TYPE_INT_ARGB);

			imgGraphics = img.createGraphics();
			imgGraphics.setPaint(Color.BLACK);
			imgGraphics.fillRect(0, 0, getWidth(), getHeight());
			imgGraphics.drawImage(oldImg, 0, 0, null);
			imgGraphics.setPaint(Color.WHITE);
			//System.out.println(img);
		}
		increment++;
		if (increment >= 5) {
			increment = 0;
			imgGraphics = img.createGraphics();
		}
		if (lightweight) {
			if (appID != null)
				EventManager.registerEvent(appID, new DefaultEvent(new Object[] {imgGraphics}, Window.this, "repaint"));
		}
		if (!oldSize.equals(getSize())) {
			oldSize = getSize();
			if (appID != null)
				EventManager.registerEvent(appID, new DefaultEvent(new Object[] {imgGraphics}, Window.this, "resize"));
		}
		if (lightweight) {
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, 0, titleHeight, null);
		}
		if (isClosed() && !disposed) {
			disposeWindow();
		}
	}
	
	public void paint(Graphics g) {
		needRepaint = true;
		super.paint(g);
		needRepaint = false;
	}
	
}
