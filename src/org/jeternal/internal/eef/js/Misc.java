package org.jeternal.internal.eef.js;

import java.io.File;

import org.jeternal.internal.Jeternal;

public class Misc {
	
	public Object loadModule(String pack, String name) {
		if (pack.equals("system")) {
			if (name.equals("ui")) {
				return new UI();
			}
			if (name.equals("event")) {
				return new EventManager();
			}
			if (name.equals("filesystem")) {
				return new FileSystem();
			}
		}
		if (pack.equals("security")) {
			if (name.equals("encryption")) {
				return new Encryption();
			}
		}
		return null;
	}
	
	public byte[] charsToBytes(char[] input) {
		byte[] bytes = new byte[input.length];
		for (int i = 0; i < input.length; i++) {
			bytes[i] = (byte) input[i];
		}
		return bytes;
	}
	
	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void open(String path) {
		Jeternal.shell(new File(path));
	}
	
}
