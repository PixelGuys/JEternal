package org.jeternal.internal.eef.js;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

import org.jeternal.internal.Jeternal;

public class Misc {
	
	private HashMap<String, Object> registeredModules = new HashMap<>();
	
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
		String corr = pack + "/" + name;
		for (String key : registeredModules.keySet()) {
			if (key.equals(corr)) {
				return registeredModules.get(corr);
			}
		}
		return null;
	}
	
	public Object loadModule(String pack) {
		return loadModule(pack.split("/")[0], pack.split("/")[1]);
	}
	
	public String generateAppID() {
		return Integer.toHexString(new Random().nextInt());
	}
	
	public void registerAppID(String appID) {
		EventManager.eventQueue.put(appID, new LinkedBlockingDeque<>());
	}
	
	public void unregisterAppID(String appID) {
		EventManager.eventQueue.put(appID, null);
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
