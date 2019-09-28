package org.jeternal.sdk.components;

import java.awt.*;

import javax.imageio.*;

import org.jeternal.sdk.*;

public class Cursors {

	public static Cursor DEFAULT_CURSOR;
	public static Cursor ACTION_CURSOR;
	public static Cursor LOADING_CURSOR;
	
	static {
		try {
			//System.out.println(ImageIO.read(FileSystem.impl_loadFile("System/Resources/Images/Cursors/DefaultCursor.png")).getWidth());
			DEFAULT_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Cursors/DefaultCursor.png")), new Point(0, 0), "Default");
			System.out.println(DEFAULT_CURSOR.getName());
			System.out.println(DEFAULT_CURSOR.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ACTION_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Cursors/ActionCursor.png")), new Point(0, 0), "Action");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			LOADING_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(FileSystem.loadJavaFile("System/Resources/Images/Cursors/LoadingCursor.png")), new Point(0, 0), "Loading");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
