package org.jeternal.sdk.io;

import java.lang.reflect.InvocationTargetException;

import org.jeternal.internal.Jeternal;
import org.jeternal.sdk.SystemComponent;

public class File {

	private SystemComponent component;
	
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
			component = c;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | NullPointerException e) {
			throw new IllegalArgumentException("Fatal Error: io.jar is not correctly loaded.");
		}
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
	
}
