package org.jeternal.sdk;

import java.io.*;

public class FileSystem {

	static String rootDisk = System.getProperty("jeternal.rootfs", "vfs");
	
	public static org.jeternal.sdk.io.File loadFile(String path) throws Exception {
		org.jeternal.sdk.io.File file = new org.jeternal.sdk.io.File(path);
		return file;
	}
	
	public static File loadJavaFile(String path) {
		File rd = new File(rootDisk);
		if (!rd.exists()) {
			rd.mkdirs();
		}
		File file = new File(rootDisk + "/" + path);
		return file;
	}

}
