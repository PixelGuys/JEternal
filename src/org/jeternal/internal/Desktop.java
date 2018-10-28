package org.jeternal.internal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jeternal.sdk.FileSystem;
import org.jeternal.sdk.components.Button;
import org.jeternal.sdk.components.Cursors;
import org.jeternal.sdk.components.Window;

public class Desktop extends JDesktopPane {

	private static final long serialVersionUID = -6251283294376465705L;

	private int selectX;
	private int selectY;
	private int selectWidth;
	private int selectHeight;
	private Button utilButton;

	private BufferedImage desktopImage;
	private JPanel taskBar;

	void missing_() {
		JInternalFrame frame = new JInternalFrame();
		frame.setTitle("Error!");
		frame.setIconifiable(true);
		frame.setResizable(true);
		frame.setMaximizable(true);
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.NORTH, new JLabel(
				"Thanks for getting the virtual \"kernel\" (SDK + Base Code + UI) ! Now let's download system resources and libraries.."));
		JButton install = new JButton("Install");
		install.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				install.setEnabled(false);
				install.setText("Installing");
				JInternalFrame fram = new JInternalFrame();
				fram.setTitle("JEternal Installation");
				fram.setResizable(true);
				JLabel state = new JLabel("..");
				fram.add(BorderLayout.NORTH, state);
				JProgressBar bar = new JProgressBar();
				fram.add(BorderLayout.CENTER, bar);
				JButton quit = new JButton("Exit");
				quit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Jeternal.shutdown();
					}

				});
				quit.setEnabled(true);
				fram.add(BorderLayout.SOUTH, quit);
				fram.setIconifiable(true);
				fram.setSize(340, 100);
				fram.setLocation(400, 400);
				fram.show();
				add(fram);
				frame.hide();
				remove(frame);
				Thread th = new Thread() {
					public void run() {
						Jeternal.install(state, bar, quit);
					}
				};
				th.start();
			}

		});
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Jeternal.shutdown();
			}

		});
		JPanel south = new JPanel();
		south.add(install);
		south.add(quit);
		frame.add(BorderLayout.SOUTH, south);
		frame.setSize(640, 320);
		frame.setLocation(400, 400);
		frame.show();
		setBackground(Color.BLUE);
		add(frame);
		revalidate();
	}

	void init0() {
		utilButton = new Button();
		utilButton.setText("Shortcutse");
		utilButton.setSize(70, 30);
		add(utilButton);
		utilButton.setOnAction(new Runnable() {

			@Override
			public void run() {
				Jeternal.shell(new File("./System/SysApps/Shortcutse.eef"));
			}

		});
		setCursor(Cursors.DEFAULT_CURSOR);
		File desktop = new File("Desktop");
		File[] files = desktop.listFiles();
		int x = 10;
		int y = 75;
		for (File file : files) {
			if (x > 160) {
				y += 85;
				x = 10;
			}
			DesktopFile component = new DesktopFile(file);
			component.setLocation(x, y);
			component.setSize(75, 75);
			add(component);
			x += component.getWidth() + 20;
		}
		taskBar = new JPanel();
		taskBar.setBackground(new Color(100, 100, 157, 163));
		taskBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(taskBar);

	}

	@SuppressWarnings("deprecation")
	Desktop() {
		setBackground(Color.WHITE);
		if (!new File("System/Components").exists()) {
			missing_();
			return;
		} else {
			File[] files = new File("System/Components").listFiles();
			if (files.length < 3) {

				missing_();
				return;
			}
		}

		// Load
		this.setBackground(Color.BLACK);
		File desktop = FileSystem.impl_loadFile("Desktop");
		if (!desktop.exists()) {
			desktop.mkdirs();
			return;
		}
		try {
			desktopImage = ImageIO.read(FileSystem.impl_loadFile("System/Ressources/Images/CustomBackground.png"));
		} catch (IOException e) {
			try {
				desktopImage = ImageIO.read(FileSystem.impl_loadFile("System/Ressources/Images/Wallpapers/JEternalBackground.png"));
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

			public void mouseReleased(MouseEvent arg0) {
				selectX = -1;
				selectY = -1;
				selectWidth = 0;
				selectHeight = 0;
			}

		};
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);

		init0();
	}

	public void add(Window w) {
		super.add(w);
		taskBar.add(w.getWindowButton());
	}

	public void remove(Window w) {
		super.remove(w);
		taskBar.remove(w.getWindowButton());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		if (utilButton != null)
			utilButton.setBounds(getWidth() / 2 - 15, 0, 70, 30);
		if (taskBar != null) {
			taskBar.setLocation(new Point(0, getHeight() - 42));
			taskBar.setSize(new Dimension(getWidth(), 42));
		}
		if (desktopImage != null)
			g.drawImage(desktopImage, 0, 0, getWidth(), getHeight(), null);
		int fX = selectX;
		int fY = selectY;
		if (selectX != 0) {
			g.setColor(new Color(127, 127, 177, 127));
			g.fillRect(fX, fY, selectWidth, selectHeight);
			g.setColor(Color.BLUE);
		}
	}

}
