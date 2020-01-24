package org.jeternal.update;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jeternal.internal.*;

public class InstallationProgram {

	public static JInternalFrame createFrame() {
		JInternalFrame frame = new JInternalFrame();
		JInternalFrame installFrame = new JInternalFrame();
		JLabel state = new JLabel("..");
		JButton quit = new JButton("Installing..");
		JProgressBar bar = new JProgressBar();
		{
			installFrame.setTitle("There Is No Game On JEternal");
			installFrame.setResizable(true);
			installFrame.add(BorderLayout.NORTH, state);
			bar.setStringPainted(true);
			installFrame.add(BorderLayout.CENTER, bar);
			quit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Jeternal.shutdown();
				}

			});
			quit.setEnabled(false);
			installFrame.add(BorderLayout.SOUTH, quit);
			installFrame.setIconifiable(true);
			installFrame.setSize(540, 100);
			installFrame.setLocation(400, 400);
			frame.hide();
		}
		{
			frame.setIconifiable(true);
			frame.setResizable(true);
			frame.setMaximizable(true);
			frame.setLayout(new BorderLayout());
			JTextArea area = new JTextArea(
					"Thanks for downloading JEternal.\n"
							+ "Click \"Install\" to install the necesarry resources from the remote repository (GitHub).\n"
							+ "Click \"Quit\" to cancel the installation."
					);
			area.setEditable(false);
			frame.add(BorderLayout.CENTER, area);
			JButton install = new JButton("Install");
			JButton quit0 = new JButton("Quit");
			quit0.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Jeternal.shutdown();
				}

			});
			install.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					install.setEnabled(false);
					install.setText("Installing");
					Jeternal.desktop.add(installFrame);
					Jeternal.desktop.remove(frame);
					frame.hide();
					installFrame.show();
					Thread th = new Thread() {
						@Override
						public void run() {
							InstallationManager.install(state, bar);
							quit.setEnabled(true);
							quit.setText("Exit");
						}
					};
					th.start();
				}

			});
			JPanel south = new JPanel();
			south.add(install);
			south.add(quit0);
			frame.add(BorderLayout.SOUTH, south);
			frame.setSize(640, 320);
			frame.setLocation(Jeternal.desktop.getWidth() / 2, Jeternal.desktop.getHeight() / 2);
			frame.show();
		}
		return frame;
	}
}