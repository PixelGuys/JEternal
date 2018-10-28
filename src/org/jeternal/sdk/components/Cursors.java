package org.jeternal.sdk.components;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jeternal.sdk.FileSystem;

public class Cursors {

	public static Cursor DEFAULT_CURSOR;
	public static Cursor ACTION_CURSOR;
	
	static {
		try {
			DEFAULT_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(FileSystem.impl_loadFile("System/Ressources/Images/Cursors/DefaultCursor.png")), new Point(0, 0), "Default");
		} catch (HeadlessException | IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
		}
		try {
			ACTION_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(FileSystem.impl_loadFile("System/Ressources/Images/Cursors/ActionCursor.png")), new Point(0, 0), "Default");
		} catch (HeadlessException | IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
