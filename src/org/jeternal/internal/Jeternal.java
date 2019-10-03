package org.jeternal.internal;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.event.*;
import org.jeternal.internal.eef.*;
import org.jeternal.sdk.*;
import org.jeternal.sdk.FileSystem;
import org.jeternal.sdk.components.Window;

public class Jeternal {

	public static JFrame jEternal;
	public static Desktop desktop;
	public final static String jEternalVersion = "19.10";
	static double renderTime = 1000000000.0 / 24;
	static HashMap<String, String> app2PathMap = new HashMap<>();
	static HashMap<String, String> ext2App = new HashMap<>();
	static LoginScreen login;

	private static byte[] charToByteArray(char[] input) {
		byte[] bytes = new byte[input.length];
		for (int i = 0; i < input.length; i++) {
			bytes[i] = (byte) input[i];
		}
		return bytes;
	}

	public static void overwriteAccount(String username, byte[] newPass) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("System/account.uac"));
			MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			byte[] d = digest.digest(newPass);
			os.writeObject(username);
			os.writeObject(d);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static JMenuItem programItem(EEFFile file, String name) {
		JMenuItem item = new JMenuItem(name);
		try {
			Image image = file.getIcon();
			if (image != null) {
				ImageIcon icon = new ImageIcon(image);
				item.setIcon(icon);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		item.addActionListener((event) -> {
			try {
				launchEEF(file.getFile());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return item;
	}

	public static void init() throws Exception {
		JMenu programs = new JMenu("Programs");
		programs.add(programItem(new EEFFile(new File("vfs/System/SysApps/notepad.eef")), "Notepad"));
		programs.add(programItem(new EEFFile(new File("vfs/System/SysApps/settings.eef")), "Settings"));
		programs.add(programItem(new EEFFile(new File("vfs/System/SysApps/pictures.eef")), "Pictures"));
		programs.add(programItem(new EEFFile(new File("vfs/System/SysApps/demo.eef")), "Demo"));
		
		JMenu computer = new JMenu("Computer");
		{
			JMenuItem powerOff = new JMenuItem("Log Out");
			powerOff.addActionListener((event) -> {
				AudioSystem.play(new File("vfs/System/Resources/Audio/shutdown.wav"));
				logout();
			});
			computer.add(powerOff);
		}
		{
			JMenuItem powerOff = new JMenuItem("Power Off");
			powerOff.addActionListener((event) -> {
				AudioSystem.play(new File("vfs/System/Resources/Audio/shutdown.wav"));
				jEternal.remove(desktop);
				FSStatusScreen status = new FSStatusScreen("Shutting down..");
				jEternal.setTitle("JEternal " + jEternalVersion);
				jEternal.setJMenuBar(null);
				jEternal.add(status);
				jEternal.revalidate();
				Thread th = new Thread(() -> {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					AudioSystem.close();
					System.exit(0);
				});
				th.start();
			});
			computer.add(powerOff);
		}
		
		JMenuBar bar = new JMenuBar();
		bar.add(programs);
		bar.add(computer);
		Jeternal.jEternal.setJMenuBar(bar);
	}
	
	public static void logout() {
		Thread th = new Thread(() -> {
			login = new LoginScreen();
			jEternal.remove(desktop);
			FSStatusScreen status = new FSStatusScreen("Logging off..");
			jEternal.setTitle("JEternal " + jEternalVersion);
			jEternal.setJMenuBar(null);
			jEternal.add(status);
			jEternal.revalidate();
			AudioSystem.waitSound();
			jEternal.remove(status);
			jEternal.add(login);
			jEternal.setSize(440, 180);
			jEternal.setLocationRelativeTo(null);
			jEternal.revalidate();
		});
		th.start();
	}

	public static void login(String username, char[] password) throws Exception {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			byte[] d = digest.digest(charToByteArray(password));

			ObjectInputStream is = new ObjectInputStream(new FileInputStream("vfs/System/account.uac"));
			String un = (String) is.readObject();
			byte[] dc = (byte[]) is.readObject();
			is.close();
			if (!MessageDigest.isEqual(d, dc) || !username.equals(un)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(jEternal, "Invalid username/password!", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(jEternal, "Encryption or Account error!");
		}

		desktop = new Desktop();
		jEternal.setSize(1280, 720);
		jEternal.setLocationRelativeTo(null);
		jEternal.remove(login);
		jEternal.add(desktop);
		jEternal.setTitle("JEternal: " + username);
		jEternal.revalidate();
		AudioSystem.play(new File("vfs/System/Resources/Audio/login.wav"));
		init();
	}

	public static void main(String[] args) throws Exception {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals("Nimbus")) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println(
				"[INFO] JEternal " + jEternalVersion + " needs a graphical interface supported by AWT."
						+ " Otherwises this will throw a HeadlessException.");
		ext2App.put("png", "Pictures Viewer");
		ext2App.put("tiff", "Pictures Viewer");
		ext2App.put("jpg", "Pictures Viewer");
		ext2App.put("jpeg", "Pictures Viewer");
		ext2App.put("txt", "Notepad");
		ext2App.put("js", "Notepad");
		ext2App.put("eef", "Launch as EEF");
		app2PathMap.put("Pictures Viewer", "/System/SysApps/pictures.eef");
		app2PathMap.put("Launch as EEF", "%eef%"); // %eef% is a predefined execvar, it is the only execvar for .eef files
		app2PathMap.put("Notepad", "/System/SysApps/notepad.eef");
		jEternal = new JFrame();
		jEternal.setTitle("JEternal " + jEternalVersion);
		jEternal.setSize(440, 180);
		jEternal.setLocationRelativeTo(null);

		jEternal.setBackground(Color.BLACK);
		jEternal.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				shutdown();
			}

		});
		AudioSystem.play(new File("vfs/System/Resources/Audio/startup.wav"));
		if (Files.exists(Paths.get("vfs/System/account.uac"))) {
			login = new LoginScreen();
			jEternal.add(login);
		} else {
			desktop = new Desktop();
			jEternal.setSize(1280, 720);
			jEternal.setLocationRelativeTo(null);
			jEternal.add(desktop);
			init();
		}
		jEternal.setVisible(true);
		Thread th = new Thread() {
			@Override
			public void run() {
				long lastRenderTime = System.nanoTime();
				//int frames = 0;
				long updatetime = 0;
				long timer = System.currentTimeMillis();
				while (true) {
					updatetime = System.currentTimeMillis();
					if (System.nanoTime() - lastRenderTime > renderTime) {
						paint();
						lastRenderTime += renderTime;
						//frames++;
					}
					updatetime = System.currentTimeMillis() - updatetime;
					int sleeptime = (1000 / 24) - (int) updatetime;
					if (sleeptime < 0) {
						sleeptime = 0;
					}
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						//System.out.println(frames + " ips");
						//frames = 0;
					}
				}
			}
		};
		th.setName("Eternal Update Thread");
		th.start();
	}

	public static void shutdown() {
		jEternal.dispose();
		System.exit(0);
	}

	static void paint() {
		jEternal.repaint();
	}

	static void update() {

	}

	public static void launchEEF(File file, Object... argv) throws Exception {
		try {
			EEFFile f = new EEFFile(file);
			EEFRunner runner = EEFRunner.launch(f, argv);
			runner.start();
		} catch (IOException e) {
			Window window = new Window();
			window.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
			window.setTitle("Invalid EEF !");
			window.add(new JLabel("Error while trying to launch this EEF file: " + e));
			window.setMaximizable(false);
			window.setResizable(false);
			window.pack();
			desktop.add(window);
			e.printStackTrace();
		}
	}

	public static String getFileAssoc(File file) {
		String fileExt = file.getName().substring(file.getName().indexOf('.') + 1);
		String appName = null;
		for (String ext : ext2App.keySet()) {
			if (ext.equals(fileExt)) {
				appName = ext2App.get(ext);
			}
		}
		return appName;
	}

	public static void shell(File file, Object... args) throws Exception {
		if (file.getName().endsWith(".eef")) {
			launchEEF(file);
			return;
		}
		String filePath = file.toString().replace("vfs" + File.separatorChar, "");
		String fileExt = file.isDirectory() ? "<$directory$>" : file.getName().substring(file.getName().indexOf('.') + 1);
		for (String ext : ext2App.keySet()) {
			if (ext.equals(fileExt)) {
				String appName = ext2App.get(ext);
				String appPath = app2PathMap.get(appName);
				System.out.println("Running " + appPath);
				if (appPath.equals("%eef%")) {
					launchEEF(file);
					return;
				}
				try {
					EEFRunner runner = EEFRunner.launch(new EEFFile(FileSystem.loadJavaFile("." + appPath)), filePath, args);
					runner.start();
				} catch (IOException e1) {
					Window window = new Window();
					window.setSize(256, 256);
					window.setLocation(desktop.getWidth() / 2 - 120, desktop.getHeight() / 2 - 120);
					window.setTitle("Application " + appName + " is invalid!");
					window.add(new JLabel("Error while trying to launch " + file.getName() + " using " + appName + " at " + appPath + ": " + e1));
					desktop.add(window);
					e1.printStackTrace();
				}
				return;
			}
		}
		Window window = new Window();
		window.setSize(256, 256);
		window.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
		window.setTitle("Open With..");
		JList<String> list = new JList<String>();
		list.setModel(new javax.swing.ListModel<String>() {

			@Override
			public String getElementAt(int index) {
				int emp = 0;
				for (String key : app2PathMap.keySet()) {
					if (emp == index) {
						return key;
					}
					emp++;
				}
				return null;
			}

			@Override
			public void addListDataListener(ListDataListener l) {

			}

			@Override
			public int getSize() {
				return app2PathMap.size();
			}

			@Override
			public void removeListDataListener(ListDataListener l) {

			}


		});
		list.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (list.getSelectedValue() != null) {
						Window win0 = new Window();
						win0.setSize(360, 360);
						win0.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
						win0.setTitle("Always Open With " + list.getSelectedValue() + " ?");
						//desktop.add(win0);
						window.hide();
						desktop.remove(window);
						ext2App.put(fileExt, list.getSelectedValue());
						try {
							shell(file);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		JScrollPane pane = new JScrollPane(list);
		pane.setSize(200, 200);
		window.add(pane);
		desktop.add(window);
	}
}