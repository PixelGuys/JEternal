package org.jeternal.internal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.jeternal.internal.eef.EEFFile;
import org.jeternal.sdk.FileSystem;
import org.jeternal.sdk.components.Button;
import org.jeternal.sdk.components.Window;
import org.jeternal.update.InstallationProgram;

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
			startButton.setIcon(ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/JEternalLogo128.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//		add(utilButton);
		add(startButton);
		utilButton.setOnAction(new Runnable() {

			@Override
			public void run() {
				Jeternal.shell(FileSystem.loadJavaFile("./System/SysApps/Shortcutse.eef"));
			}

		});
		System.out.println(java.awt.BorderLayout.CENTER);
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
						Image ic = ef.getIcon();
						if (ic != null) {
							icon = ic;
						}
						ef.close();
					}
				}
				component.icon = icon;
			} catch (IOException e) {
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
		} catch (IllegalStateException e) {
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
		} catch (IOException e) {
			try {
				desktopImage = ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Wallpapers/JEternalBackground.png"));
			} catch (IOException e1) {
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
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		if (utilButton != null) {
			utilButton.setBounds(getWidth() / 2 - 15, 0, 70, 30);
		}
		if (startButton != null) {
			startButton.setBounds(0, getHeight() - 42, 42, 42);
		}
		if (taskBar != null) {
			taskBar.setLocation(new Point(42, getHeight() - 42));
			taskBar.setSize(new Dimension(getWidth() - 42, 42));
		}
		if (desktopImage != null) {
			g.drawImage(desktopImage, 0, 0, getWidth(), getHeight(), null);
		}
		int fX = selectX;
		int fY = selectY;
		if (selectX != 0) {
			g.setColor(new Color(127, 127, 177, 153));
			g.fillRect(fX, fY, selectWidth, selectHeight);
			g.setColor(Color.BLUE);
		}
		g.setColor(Color.white);
		if (taskBar != null) {
			g.drawString("JEternal " + Jeternal.jEternalVersion, 0, getHeight() - taskBar.getHeight());
			JInternalFrame frame = getSelectedFrame();
			if (frame != null) {
				g.drawString("Selected frame:", 0, 12);
				g.drawString("X = " + frame.getX(), 0, 24);
				g.drawString("Y = " + frame.getY(), 0, 36);
				g.drawString("Width = " + frame.getWidth(), 0, 48);
				g.drawString("Height = " + frame.getHeight(), 0, 60);
			}
		} else {
			g.drawString("JEternal " + Jeternal.jEternalVersion, 0, getHeight());
		}
		//Component c = lowestComponentAt(this, mouseX, mouseY);

		//System.out.println(c);
		//if (c instanceof JEComponent) {
		//	g.drawImage(((JEComponent) c).getLightWeightCursor(), mouseX, mouseY, null);
		//}
	}

}
