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
		return getIcon(IconSize.DEFAULT);
	}
	
	public Image getIcon(IconSize size) throws Exception {
		if (image == null) {
			String name = "res/icon";
			if (size == IconSize.HIGHEST) {
				Image icon = getIcon(IconSize.X256);
				if (icon == null) {
					icon = getIcon(IconSize.X128);
					if (icon == null) {
						icon = getIcon(IconSize.X64);
						if (icon == null) {
							icon = getIcon(IconSize.X32);
							if (icon == null) {
								return getIcon(IconSize.X16);
							} else {
								return icon;
							}
						} else {
							return icon;
						}
					} else {
						return icon;
					}
				} else {
					return icon;
				}
			}
			if (size == IconSize.X32) {
				name += "32";
			}
			if (size == IconSize.X16) {
				name += "16";
			}
			if (size == IconSize.X64) {
				name += "64";
			}
			if (size == IconSize.X128) {
				name += "128";
			}
			if (size == IconSize.X256) {
				name += "256";
			}
			name += ".png";
			ZipEntry iconFile = getEntry(name);
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
