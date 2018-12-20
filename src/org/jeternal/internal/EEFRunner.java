package org.jeternal.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jeternal.internal.eef.EEFFile;
import org.jeternal.internal.eef.Manifest;
import org.jeternal.internal.eef.js.Misc;
import org.jeternal.internal.eef.ESLFile;

public class EEFRunner {

	private Object[] argv;
	private ScriptEngine engine;
	
	public static EEFRunner launch(File file, Object... argv) throws IOException {
		EEFRunner runner = new EEFRunner(file);
		runner.argv = argv;
		return runner;
	}

	public PrintStream getProgrammOut() {
		return programmOut;
	}

	public void setProgrammOut(PrintStream programmOut) {
		this.programmOut = programmOut;
		if (engine != null) {
			engine.put("out", programmOut);
		}
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
	
	private PrintStream programmOut = System.out;
	private Reader programmIn = new InputStreamReader(System.in);
	private File file;
	private EEFFile eef;
	
	void runJS(ZipEntry entry) throws IOException {
		runJS(eef.getInputStream(entry));
	}
	
	void runJS(InputStream in) throws IOException {
		System.setProperty("nashorn.args", "--language=es6");
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByMimeType("text/javascript");
		System.out.println(engine);
		try {
			engine.put("out", programmOut);
			engine.put("misc", new Misc());
			engine.put("argv", argv);
			engine.put("LoadLibrary", (Function<String, ESLFile>) (string) -> {
				return ESLLoader.load(new File(string));
			});
			// Start
			engine.eval(new InputStreamReader(in));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		// End
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

	EEFRunner(File file) throws IOException {
		this.file = file;
	}

}
