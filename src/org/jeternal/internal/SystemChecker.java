package org.jeternal.internal;

import java.io.*;

import org.jeternal.sdk.*;

public class SystemChecker {

	static String[] expected = new String[] {
		"System/Libraries/ete64.esl",
		"System/SysApps/settings.eef"
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
