package org.jeternal.internal;

import java.io.File;

import org.jeternal.sdk.FileSystem;

public class SystemChecker {

	static String[] expected = new String[] {
			"System/Components",
			"System/Components/io.jar"
	};
	
	public static void checkIntegrity() {
		for (String str : expected) {
			File f = FileSystem.loadJavaFile(str);
			if (!f.exists()) {
				throw new IllegalStateException("Non-integral system: missing resource \"" + str + "\"");
			}
		}
	}
	
}