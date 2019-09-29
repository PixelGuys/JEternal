package org.jeternal.internal;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;

import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JButton btnLogin;
	private JPanel panel_1;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	
	public LoginScreen() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		add(panel, BorderLayout.SOUTH);
		
		btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(211, 211, 211));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Jeternal.login(textField.getText(), passwordField.getPassword());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnLogin);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(220, 220, 220));
		add(panel_1, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.requestFocus();
			}
		});
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Jeternal.login(textField.getText(), passwordField.getPassword());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		lblUsername = new JLabel("Username:");
		
		lblPassword = new JLabel("Password:");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUsername)
						.addComponent(lblPassword))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
					.addGap(18))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(201, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);

	}
}
