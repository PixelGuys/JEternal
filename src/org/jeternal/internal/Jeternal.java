package org.jeternal.internal;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import org.jeternal.sdk.FileSystem;
import org.jeternal.sdk.SystemComponent;
import org.jeternal.sdk.SystemLibrary;
import org.jeternal.sdk.components.Window;

public class Jeternal {

	private static Frame jEternal;
	public static Desktop desktop;
	private final static String jEternalVersion = "Blue, Build 381";
	static double tickTime = 1000000000.0 / 24;
	static double renderTime = 1000000000.0 / 24;
	public static SystemLibrary IO_LIB;
	public static SystemComponent IO_MAIN_COMPONENT;
	static HashMap<String, String> app2PathMap = new HashMap<>();
	static HashMap<String, String> ext2App = new HashMap<>();

	public static void main(String[] args) {
		System.out.println(
				"[OS] JEternal " + jEternalVersion + " needs a X11-compatible"
						+ " environment. Min. Java Version: 1.5. ");
		ext2App.put("png", "Pictures Viewer");
		ext2App.put("tiff", "Pictures Viewer");
		ext2App.put("jpg", "Pictures Viewer");
		ext2App.put("jpeg", "Pictures Viewer");
		app2PathMap.put("Pictures Viewer", "/System/SysApps/Pictures.eef");
		app2PathMap.put("Launch as EEF", "%eef%"); // %eef% is a predefined execvar, it is the only execvar for .eef files
		app2PathMap.put("EternalELF", "/Programs/EternalELF/eternalelf.eef %1%"); // EternalELF is an application, implemented by default in Jeternal
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
		jEternal = new JFrame();
		jEternal.setTitle("JEternal " + jEternalVersion);
		jEternal.setSize(1280, 720);
		//jEternal.setUndecorated(true);
		//jEternal.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jEternal.setBackground(Color.BLACK);
		jEternal.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				shutdown();
			}

		});
		desktop = new Desktop();
		jEternal.add(desktop);
		jEternal.setVisible(true);

		Thread th = new Thread() {
			public void run() {
				long lastTickTime = System.nanoTime();
				long lastRenderTime = System.nanoTime();
				int frames = 0;
				int ticks = 0;
				long updatetime = 0;
				long timer = System.currentTimeMillis();
				while (true) {
					updatetime = System.currentTimeMillis();
					if (System.nanoTime() - lastTickTime > tickTime) {
						update();
						lastTickTime += tickTime;
						ticks++;
					} if (System.nanoTime() - lastRenderTime > renderTime) {
						paint();
						lastRenderTime += renderTime;
						frames++;
					}
					updatetime = System.currentTimeMillis() - updatetime;
					int sleeptime = (1000 / 60) - (int) updatetime;
					if (sleeptime < 0) sleeptime = 0;
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						if (true)
							System.out.println(ticks + " ticks, " + frames + " ips");
						ticks = 0;
						frames = 0;
					}
				}
			}
		};
		th.setName("Eternal Update Thread");
		th.start();
		
		if (!new File("System/Components").exists()) {
			return;
		}
		
		SystemLibrary lib = FileSystem.SystemRessourcesLoader.loadSystemLibrary("io");
		try {
			lib.loadComponent("io_system").comp_("init");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}

	}
	
	static void install(JLabel state, JProgressBar bar, JButton exit) {
		try {
			String baseURL = "file:///C:/Users/UserPC/Documents/GitHub/JEternal";
			URL installInst = new URL(baseURL + "/install_content");
			InputStream instIn = installInst.openStream();
			Scanner sc = new Scanner(instIn);
			String curLabel = "";
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] args = line.split(" ");
				if (args[0].equals("LABEL")) {
					state.setText(line.replaceFirst("LABEL ", ""));
					curLabel = line.replaceFirst("LABEL ", "");
				}
				if (args[0].equals("DOWN")) {
					state.setText(curLabel + ": " + args[1]);
					URL url = new URL(baseURL + "/" + args[1]);
					InputStream in = url.openStream();
					File file = new File(args[1]);
					FileOutputStream out = new FileOutputStream(file);
					while (in.available() != 0) {
						out.write(in.read());
					}
					out.close();
					in.close();
				}
				if (args[0].equals("MKDIR")) {
					File dir = new File(args[1]);
					dir.mkdir();
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		state.setText("Finished ! Please restart JEternal");
		bar.setValue(100);
		exit.setEnabled(true);
	}

	static void shutdown() {
		jEternal.dispose();
		// TODO: Do exit finalizations
		System.exit(0);
	}

	static void paint() {
		desktop.repaint();
	}

	static void update() {

	}
	
	public static void shell(File file) {
		if (file.getName().endsWith(".eef")) {
			try {
				EEFRunner runner = EEFRunner.launch(file);
				System.out.println("[OS] ["+file+"] Is App Signed: " + runner.isSigned());
				runner.start();
			} catch (IOException e) {
				Window window = new Window();
				window.setSize(256, 256);
				window.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
				window.setTitle("Invalid EEF !");
				window.add(new JLabel("Error while trying to launch this EEF file: " + e));
				desktop.add(window);
				e.printStackTrace();
			}
			return;
		}
		String fileExt = file.getName().substring(file.getName().indexOf('.') + 1);
		for (String ext : ext2App.keySet()) {
			if (ext.equals(fileExt)) {
				String appName = ext2App.get(ext);
				String appPath = app2PathMap.get(appName);
				System.out.println("Running " + appPath);
				try {
					EEFRunner runner = EEFRunner.launch(new File("." + appPath), file);
					System.out.println("[OS] ["+appPath+"] Is App Signed: " + runner.isSigned());
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
		if (file.getName().endsWith(".jar")) {
			try {
				URLClassLoader cld = new URLClassLoader(new URL[] {file.toURI().toURL()}, Jeternal.class.getClassLoader());
				System.out.println(cld);
				Class<?> cl = cld.loadClass("jcomp.Compiler");
				cl.getMethod("main", String[].class).invoke(null, new Object[] {new String[] {}});
				cld.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		}
		Window window = new Window();
		window.setSize(256, 256);
		window.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
		window.setTitle("Open With..");
		JList<String> list = new JList<String>();
		list.setModel(new javax.swing.ListModel<String>() {

			private static final long serialVersionUID = 1L;
			
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
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (list.getSelectedValue() != null) {
						Window win0 = new Window();
						win0.setSize(360, 360);
						win0.setLocation(desktop.getWidth() / 2 - 100, desktop.getHeight() / 2 - 100);
						win0.setTitle("Always Open With " + list.getSelectedValue() + " ?");
						desktop.add(win0);
						window.hide();
						desktop.remove(window);
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
