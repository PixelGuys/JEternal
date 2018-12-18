package org.jeternal.internal.eef.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.jeternal.sdk.io.File;

public class FileSystem {

	public File file(String path) {
		return new File(path);
	}
	
	public InputStream fileInput(File file) {
		try {
			return new FileInputStream(new java.io.File(file.getPath()));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public OutputStream fileOutput(File file) {
		try {
			return new FileOutputStream(new java.io.File(file.getPath()));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public ObjectOutputStream fileObjectOutput(File file) {
		try {
			return new ObjectOutputStream(fileOutput(file));
		} catch (IOException e) {
			return null;
		}
	}
	
	public ObjectInputStream fileObjectInput(File file) {
		try {
			return new ObjectInputStream(fileInput(file));
		} catch (IOException e) {
			return null;
		}
	}
	
}
