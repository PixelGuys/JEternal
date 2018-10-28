package org.jeternal.sdk;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class SystemLibrary {

	private URL url;
	private URLClassLoader cld;
	
	public SystemLibrary(URL libraryUrl) 
	{
		this.url = libraryUrl;
	}
	
	public SystemComponent loadComponent(String component) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (cld == null)
			cld = new URLClassLoader(new URL[] {url}, SystemLibrary.class.getClassLoader());
		Class<?> cl = cld.loadClass(component);
		if (SystemComponent.class.isAssignableFrom(cl)) {
			return (SystemComponent) cl.getConstructor().newInstance();
		}
		return null;
	}
	
}
