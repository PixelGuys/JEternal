package org.jeternal.internal;

import java.io.File;
import java.io.IOException;

import org.jeternal.internal.eef.ESLFile;

public class ESLLoader {

	public static ESLFile load(File path) {
		try {
			return new ESLFile(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
