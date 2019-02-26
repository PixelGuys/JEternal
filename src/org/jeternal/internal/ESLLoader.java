package org.jeternal.internal;

import java.io.File;
import java.io.IOException;

import org.jeternal.internal.eef.ESLFile;

import jdk.nashorn.api.scripting.NashornScriptEngine;

public class ESLLoader {

	public static ESLFile load(NashornScriptEngine nse, File path) {
		try {
			return new ESLFile(nse, new File("vfs", path.toString()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
