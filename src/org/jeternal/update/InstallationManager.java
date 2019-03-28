package org.jeternal.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.jeternal.sdk.FileSystem;

public class InstallationManager {

	static int steps;
	public static final String REPO_URL = "https://raw.githubusercontent.com/PixelGuys/JEternal/master";

	public static String getLatestVersion() {
		try {
			URL installInst = new URL(REPO_URL + "/install_content");
			InputStream instIn = installInst.openStream();
			Scanner sc = new Scanner(instIn);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				if (line.startsWith("VER")) {
					String ver = line.replaceFirst("VER", "");
					return ver;
				}
			}
			sc.close();
			instIn.close();
			return "none";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void install(JLabel state, JProgressBar bar) {
		try {
			URL installInst = new URL(REPO_URL + "/install_content");
			InputStream instIn = installInst.openStream();
			String str = new String(instIn.readAllBytes());
			instIn.close();
			Scanner sc = new Scanner(str);
			String curLabel = "";
			//steps = 0;
			steps = (int) sc.findAll(Pattern.compile("(\\r\\n|\\r|\\n)")).count();
			sc.close();
			sc = new Scanner(str);
			bar.setMaximum(steps);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] args = line.split(" ");
				if (args[0].equals("LABEL")) {
					state.setText(line.replaceFirst("LABEL ", ""));
					curLabel = line.replaceFirst("LABEL ", "");
				}
				if (args[0].equals("DOWN")) {
					state.setText(curLabel + ": " + args[1]);
					URL url = new URL(REPO_URL + "/vfs/" + args[1]);
					InputStream in = url.openStream();
					File file = FileSystem.loadJavaFile(args[1]);
					FileOutputStream out = new FileOutputStream(file);
					in.transferTo(out);
					out.close();
					in.close();
				}
				if (args[0].equals("MKDIR")) {
					File dir = FileSystem.loadJavaFile(args[1]);
					dir.mkdir();
				}
				bar.setValue(bar.getValue() + 1);
				//Thread.sleep(1000); // only for step-by-step install
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		state.setText("Finished ! Please restart JEternal");
		bar.setValue(100);
	}
	
}
