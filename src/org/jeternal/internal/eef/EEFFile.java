package org.jeternal.internal.eef;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EEFFile extends ZipFile {

	private Image image;
	
	public EEFFile(File file) throws IOException {
		super(file);
	}
	
	public Image getIcon() throws IOException {
		if (image == null) {
			ZipEntry iconFile = getEntry("res/icon.png");
			if (iconFile != null) {
				image = ImageIO.read(getInputStream(iconFile));
			}
		}
		return image;
	}
	
	public Manifest getManifest() throws IOException {
		ZipEntry mfFile = getEntry(".MANIFEST");
		if (mfFile == null) {
			return null;
		}
		InputStream is = getInputStream(mfFile);
		Manifest mf = new Manifest(is);
		return mf;
	}
	
}
