package org.jeternal.sdk.io;

import java.lang.reflect.InvocationTargetException;

import org.jeternal.internal.Jeternal;
import org.jeternal.sdk.SystemComponent;

public class File {

	private SystemComponent component;
	private String path;
	
	public static File createFromSystemComponent(SystemComponent component) {
		File file = new File(component);
		return file;
	}
	
	private File(SystemComponent component) {
		this.component = component;
	}
	
	public File(String path) {
		try {
			SystemComponent c = Jeternal.IO_LIB.loadComponent("file");
			c.comp_("init", path);
			this.path = path;
			component = c;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | NullPointerException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Fatal Error: io.jar is not correctly loaded.");
		}
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return path.substring(path.lastIndexOf('/') + 1);
	}
	
	public String getExtension() {
		return getName().substring(getName().lastIndexOf('.') + 1);
	}
	
	public boolean create() {
		return (boolean) component.comp_("create");
	}
	
	public boolean delete() {
		return (boolean) component.comp_("delete");
	}
	
	public boolean rename(String newName) {
		return (boolean) component.comp_("rename", newName);
	}
	
	public boolean exists() {
		return (boolean) component.comp_("exists");
	}
	
	public File[] list() {
		String[] paths = (String[]) component.comp_("list");
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		return files;
	}
	
}
