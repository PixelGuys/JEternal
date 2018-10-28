package org.jeternal.internal.eef;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EEFFile extends ZipFile {

	public EEFFile(File file) throws IOException {
		super(file);
	}
	
	public Manifest getManifest() throws IOException {
		ZipEntry mfFile = getEntry("MANIFEST.manifest");
		if (mfFile == null) {
			return null;
		}
		InputStream is = getInputStream(mfFile);
		Manifest mf = new Manifest(is);
		return mf;
	}
	
}
