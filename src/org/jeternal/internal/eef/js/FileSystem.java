package org.jeternal.internal.eef.js;

import java.io.*;

import org.jeternal.sdk.io.File;

public class FileSystem {

	public File file(String path) throws Exception {
		return new File(path);
	}
	
	public InputStream fileInput(File file) {
		try {
			return new FileInputStream(new java.io.File("vfs/" + file.getPath()));
		} catch (Exception e) {
			return null;
		}
	}
	
	public OutputStream fileOutput(File file) {
		try {
			return new FileOutputStream(new java.io.File("vfs/" + file.getPath()));
		} catch (Exception e) {
			return null;
		}
	}
	
	public ObjectOutputStream fileObjectOutput(File file) {
		try {
			return new ObjectOutputStream(fileOutput(file));
		} catch (Exception e) {
			return null;
		}
	}
	
	public ObjectInputStream fileObjectInput(File file) {
		try {
			return new ObjectInputStream(fileInput(file));
		} catch (Exception e) {
			return null;
		}
	}
	
}
