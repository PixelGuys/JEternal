package org.jeternal.sdk;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static org.jeternal.internal.Jeternal.*;

public class FileSystem {

	public static org.jeternal.sdk.io.File loadFile(String path) {
		if (IO_LIB == null) {
			IO_LIB = SystemRessourcesLoader.loadSystemLibrary("io");
			try {
				IO_MAIN_COMPONENT = IO_LIB.loadComponent("io_system");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		org.jeternal.sdk.io.File file = new org.jeternal.sdk.io.File(path);
		return file;
	}
	
	@Deprecated
	public static File impl_loadFile(String path) {
		File file = new File(path);
		return file;
	}
	
	public static class SystemRessourcesLoader {
		
		public static SystemLibrary loadSystemLibrary(String path) 
		{
			File file = new File("System/Components/"+path+".jar");
			try {
				return new SystemLibrary(file.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
}
