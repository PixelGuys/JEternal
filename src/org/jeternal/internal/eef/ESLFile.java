package org.jeternal.internal.eef;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ESLFile extends ZipFile {

	public ESLFile(File file) throws ZipException, IOException {
		super(file);
	}
	
	public ESLModule getESLModule() throws IOException {
		ZipEntry mfFile = getEntry("library.mod");
		if (mfFile == null) {
			return null;
		}
		InputStream is = getInputStream(mfFile);
		ESLModule eslm = new ESLModule(is);
		return eslm;
	}

}
