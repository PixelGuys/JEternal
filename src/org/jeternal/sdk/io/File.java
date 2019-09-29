package org.jeternal.sdk.io;

import java.io.IOException;

public class File {

	private java.io.File peer;
	private String path;
	
	public File(java.io.File peer) {
		this.peer = peer;
	}
	
	public File(String path) {
		this.path = "vfs/" + path;
		this.peer = new java.io.File(this.path);
	}
	
	public String getPath() {
		return path.substring(4);
	}
	
	public String getName() {
		return path.substring(path.lastIndexOf('/') + 1);
	}
	
	public String getExtension() {
		return getName().substring(getName().lastIndexOf('.') + 1);
	}
	
	public boolean create() {
		try {
			return peer.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean delete() {
		return peer.delete();
	}
	
	public boolean rename(String newName) {
		return peer.renameTo(new java.io.File(path, newName));
	}
	
	public boolean exists() {
		return peer.exists();
	}
	
	public File[] list() throws Exception {
		String[] paths = (String[]) peer.list();
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		return files;
	}
	
}
