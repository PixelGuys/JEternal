package org.jeternal.internal.eef;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class ESLFile extends ZipFile {

	private NashornScriptEngine nse;
	
	public ESLFile(NashornScriptEngine nse,File file) throws ZipException, IOException {
		super(file);
		this.nse = nse;
	}
	
	public NashornScriptEngine getScriptEngine() {
		return nse;
	}
	
	public ESLModule getESLModule() throws IOException {
		ZipEntry mfFile = getEntry("library.mod");
		if (mfFile == null) {
			return null;
		}
		InputStream is = getInputStream(mfFile);
		ESLModule eslm = new ESLModule(this, is);
		return eslm;
	}

}
