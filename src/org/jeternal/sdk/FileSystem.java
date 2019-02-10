package org.jeternal.sdk;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static org.jeternal.internal.Jeternal.*;

public class FileSystem {

	static String rootDisk = System.getProperty("jeternal.rootfs", "vfs");
	
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
	
	public static File loadJavaFile(String path) {
		File rd = new File(rootDisk);
		if (!rd.exists()) {
			rd.mkdirs();
		}
		File file = new File(rootDisk + "/" + path);
		return file;
	}

	public static class SystemRessourcesLoader {

		public static SystemLibrary loadSystemLibrary(String path)
		{
			File file = FileSystem.loadJavaFile("System/Components/"+path+".jar");
			if (!file.exists()) {
				return null;
			}
			try {
				return new SystemLibrary(file.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

}
