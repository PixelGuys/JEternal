package org.jeternal.internal.eef;

import java.io.*;
import java.util.zip.*;

import jdk.nashorn.api.scripting.*;

@SuppressWarnings("removal")
public class ESLFile extends ZipFile {

	private NashornScriptEngine nse;
	
	public ESLFile(NashornScriptEngine nse,File file) throws Exception {
		super(file);
		this.nse = nse;
	}
	
	public NashornScriptEngine getScriptEngine() {
		return nse;
	}
	
	public ESLModule getESLModule() throws Exception {
		ZipEntry mfFile = getEntry("library.mod");
		if (mfFile == null)
			return null;
		InputStream is = getInputStream(mfFile);
		ESLModule eslm = new ESLModule(this, is);
		return eslm;
	}
}