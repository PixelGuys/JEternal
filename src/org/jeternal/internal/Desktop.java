package org.jeternal.internal;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import org.jeternal.internal.eef.*;
import org.jeternal.sdk.*;
import org.jeternal.sdk.components.*;
import org.jeternal.sdk.components.Button;
import org.jeternal.sdk.components.Window;
import org.jeternal.update.*;

@SuppressWarnings("unused")
public class Desktop extends JDesktopPane {

	private static final long serialVersionUID = -6251283294376465705L;

	private int selectX;
	private int selectY;
	private int selectWidth;
	private int selectHeight;
	private Button utilButton;
	private Button startButton;

	private BufferedImage desktopImage;
	private JPanel taskBar;

	private BufferedImage currentCursor;

	void missing_() {
		setBackground(Color.BLUE);
		add(InstallationProgram.createFrame());
		revalidate();
	}

	void init0() {
		utilButton = new Button();
		utilButton.setText("Shortcutse");
		utilButton.setSize(70, 30);

		try {
			startButton = new Button();
			startButton.setSize(32, 32);
			startButton.setFullIcon(true);
			startButton.setIcon(ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/JEternalLogo32.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		add(startButton);
		utilButton.setOnAction(new Runnable() {

			@Override
			public void run() {
				try {
					Jeternal.shell(FileSystem.loadJavaFile("./System/SysApps/Shortcutse.eef"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		refreshDesktop();
		taskBar = new JPanel();
		taskBar.setBackground(new Color(100, 100, 157, 163));
		taskBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		taskBar.setOpaque(true);
		add(taskBar);

	}
	
	public void refreshDesktop() {
		for (Component c : getComponents()) {
			if (c instanceof DesktopFile) {
				remove(c);
			}
		}
		File desktop = FileSystem.loadJavaFile("Desktop");
		File[] files = desktop.listFiles();
		int x = 10;
		int y = 75;
		for (File file : files) {
			if (x > 300) {
				y += 85;
				x = 10;
			}
			DesktopFile component = new DesktopFile(file);
			component.setLocation(x, y);
			component.setSize(75, 75);
			try {
				Image icon = ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/FileDefaultIcon.png"));
				String appName = Jeternal.getFileAssoc(file);
				if (appName != null) {
					if (appName.equals("Launch as EEF")) {
						EEFFile ef = new EEFFile(file);
						Image ic = ef.getIcon(IconSize.X256);
						if (ic != null) {
							icon = ic;
						}
						ef.close();
					}
				}
				component.icon = icon;
			} catch (Exception e) {
				e.printStackTrace();
			}
			add(component);
			x += component.getWidth() + 20;
		}
	}
	
	JPopupMenu createPopupMenu() {
		JPopupMenu pm = new JPopupMenu();
		JMenu mnew = new JMenu("New");
		JMenuItem mnewT = new JMenuItem("Text File (.txt)");
		mnew.add(mnewT);
		JMenuItem ref = new JMenuItem("Refresh");
		ref.addActionListener((event) -> {
			refreshDesktop();
		});
		pm.add(ref);
		pm.addSeparator();
		pm.add(mnew);
		return pm;
	}

	int mouseX, mouseY;
	Desktop() {
		Jeternal.desktop = this;
		setBackground(Color.WHITE);
		
		try {
			SystemChecker.checkIntegrity();
		} catch (Exception e) {
			System.err.println(e.getMessage() + ", starting installation");
			missing_();
			return;
		}

		// Load
		this.setBackground(Color.BLACK);
		File desktop = FileSystem.loadJavaFile("Desktop");
		if (!desktop.exists()) {
			desktop.mkdirs();
		}
		try {
			desktopImage = ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Wallpapers/CustomWallpaper.png"));
		} catch (Exception e) {
			try {
				desktopImage = ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Wallpapers/JEternalBackground.png"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		// Graphic
		MouseAdapter adapter = new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (selectX != -1 && selectY != -1) {
					selectWidth = arg0.getX() - selectX;
					selectHeight = arg0.getY() - selectY;
				} else {
					selectX = arg0.getX();
					selectY = arg0.getY();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectX = -1;
				selectY = -1;
				selectWidth = 0;
				selectHeight = 0;
			}

		};
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		this.setComponentPopupMenu(createPopupMenu());
		init0();
	}

	public void add(Window w) {
		super.add(w);
		taskBar.add(w.getWindowButton());
		taskBar.repaint();
	}

	public void remove(Window w) {
		super.remove(w);
		taskBar.remove(w.getWindowButton());
		taskBar.repaint();
	}

	public Component lowestComponentAt(Container parent, int x, int y) {
		Component c = SwingUtilities.getDeepestComponentAt(parent, x, y);
		if (c != null && c != parent) {
			if (c instanceof Container) {
				Component ci = lowestComponentAt((Container) c, x, y);
				//System.out.println(ci);
				if (ci != null) {
					return ci;
				}
			}
		}
		return c;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		if (taskBar != null) {
			g.drawString("JEternal " + Jeternal.jEternalVersion, 0, getHeight() - taskBar.getHeight());
			/*
			JInternalFrame frame = getSelectedFrame();
			if (frame != null) {
				g.drawString("Selected frame:", 0, 12);
				g.drawString("X = " + frame.getX(), 0, 24);
				g.drawString("Y = " + frame.getY(), 0, 36);
				g.drawString("Width = " + frame.getWidth(), 0, 48);
				g.drawString("Height = " + frame.getHeight(), 0, 60);
			}
			*/
		} else {
			g.drawString("JEternal " + Jeternal.jEternalVersion, 0, getHeight());
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (utilButton != null)
			utilButton.setBounds(getWidth() / 2 - 15, 0, 70, 30);
		if (startButton != null)
			startButton.setBounds(0, getHeight() - 42, 42, 42);
		if (taskBar != null) {
			taskBar.setLocation(new Point(42, getHeight() - 42));
			taskBar.setSize(new Dimension(getWidth() - 42, 42));
		}
		if (desktopImage != null)
			g.drawImage(desktopImage, 0, 0, getWidth(), getHeight(), null);
		int fX = selectX;
		int fY = selectY;
		if (selectX != 0) {
			g.setColor(new Color(127, 127, 177, 153));
			g.fillRect(fX, fY, selectWidth, selectHeight);
			g.setColor(Color.BLUE);
		}
	}
}