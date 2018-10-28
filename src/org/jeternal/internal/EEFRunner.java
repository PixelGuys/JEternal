package org.jeternal.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.zip.ZipEntry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jeternal.internal.eef.EEFFile;
import org.jeternal.internal.eef.Manifest;
import org.jeternal.internal.eef.js.Misc;

public class EEFRunner {

	public static EEFRunner launch(File file) throws IOException {
		EEFRunner runner = new EEFRunner(file);
		return runner;
	}

	public PrintStream getProgrammOut() {
		return programmOut;
	}

	public void setProgrammOut(PrintStream programmOut) {
		this.programmOut = programmOut;
	}

	public Reader getProgrammIn() {
		return programmIn;
	}

	public void setProgrammIn(Reader programmIn) {
		this.programmIn = programmIn;
	}

	public File getFile() {
		return file;
	}

	boolean signed = false;
	private PrintStream programmOut = System.out;
	private Reader programmIn = new InputStreamReader(System.in);
	private File file;
	private byte[] memory;
	private int empty;
	private EEFFile eef;
	private static int max = 1000 * 1000 * 1000; // 1 GO

	public String rmem_8str(int addr) {
		StringBuilder builder = new StringBuilder();
		int i = addr;
		while (true) {
			byte b = memory[i];
			builder.append( (char) b);
			if (b == '\0')
				break;
		}
		return builder.toString();
	}
	
	public int wmem_8str(int addr, String str) {
		for (byte b : str.getBytes()) {
			memory[empty + addr] = b;
		}
		return empty + addr;
	}
	
	void runJS(ZipEntry entry) throws IOException {
		runJS(eef.getInputStream(entry));
	}
	
	void runJS(InputStream in) throws IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
		System.out.println(engine);
		Object obj = null;
		try {
			obj = engine.eval("java.lang.System.gc");
		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(obj);
		try {
			engine.put("out", programmOut);
			engine.put("misc", new Misc());
			engine.eval(new InputStreamReader(in));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public void start() throws IOException {
		Thread th = new Thread() {
			public void run() {
				try {
					eef = new EEFFile(file);
					Manifest mf = eef.getManifest();
					ZipEntry main = eef.getEntry("js/main.js");
					runJS(main);
					eef.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.setName("EEF-"+file);
		th.start();
	}

	public boolean isSigned() {
		return signed;
	}

	EEFRunner(File file) throws IOException {
		this.file = file;
	}

}
