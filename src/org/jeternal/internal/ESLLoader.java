package org.jeternal.internal;

import java.io.*;

import org.jeternal.internal.eef.*;

import jdk.nashorn.api.scripting.*;

@SuppressWarnings("removal")
public class ESLLoader {

	public static ESLFile load(NashornScriptEngine nse, File path) {
		try {
			return new ESLFile(nse, new File("vfs", path.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
