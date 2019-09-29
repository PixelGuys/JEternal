package org.jeternal.internal.eef;

import java.util.zip.*;

import javax.imageio.*;

import java.awt.*;
import java.io.*;

public class EEFFile extends ZipFile {

	private Image image;
	private File file;
	
	public EEFFile(File file) throws Exception {
		super(file);
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public Image getIcon() throws Exception {
		if (image == null) {
			ZipEntry iconFile = getEntry("res/icon.png");
			if (iconFile != null) {
				image = ImageIO.read(getInputStream(iconFile));
			}
		}
		return image;
	}
	
	public Manifest getManifest() throws Exception {
		ZipEntry mfFile = getEntry(".MANIFEST");
		if (mfFile == null) {
			return null;
		}
		InputStream is = getInputStream(mfFile);
		Manifest mf = new Manifest(is);
		return mf;
	}
	
}
