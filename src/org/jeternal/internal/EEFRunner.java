package org.jeternal.internal;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.zip.*;

import javax.imageio.*;
import javax.script.*;

import org.jeternal.internal.eef.*;
import org.jeternal.internal.eef.js.*;

import jdk.nashorn.api.scripting.*;

@SuppressWarnings({"removal" , "unused"})
public class EEFRunner {

	private Object[] argv;
	private NashornScriptEngine engine;
	private static Map<String, CompiledScript> cache = new HashMap<>();
	private Image icon;
	public static final boolean ENABLE_SCRIPT_CACHING = true;
	
	static class DefaultClassFilter implements ClassFilter {

		@Override
		public boolean exposeToScripts(String className) {
			return className.equals("java.awt.BorderLayout");
		}
		
	}
	
	public static EEFRunner launch(EEFFile file, Object... argv) throws IOException {
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
	
	public Image getIcon() {
		return icon;
	}
	
	void runJS(ZipEntry entry) throws IOException {
		runJS(eef.getInputStream(entry));
	}
	
	void runJS(InputStream in) throws IOException {
		System.setProperty("nashorn.args", "--language=es6");
		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
		engine = (NashornScriptEngine) factory.getScriptEngine(new String[] {"--language=es6"}, EEFRunner.class.getClassLoader(), new DefaultClassFilter());
		engine.getContext().setAttribute(ScriptEngine.ARGV, argv, ScriptContext.ENGINE_SCOPE);
		//System.out.println(engine);
		try {
			engine.put("exit", null);
			engine.put("quit", null);
			engine.put("out", programmOut);
			engine.put("misc", new Misc());
			engine.put("argv", argv);
			engine.put("LoadLibrary", (Function<String, ESLFile>) (string) -> {
				return ESLLoader.load(engine, new File(string));
			});
			// Start
			if (eef != null && ENABLE_SCRIPT_CACHING) {
				if (cache.containsKey(eef.getName())) {
					cache.get(eef.getName()).eval(engine.getContext());
				} else {
					cache.put(eef.getName(), engine.compile(new InputStreamReader(in)));
					cache.get(eef.getName()).eval();
				}
			} else {
				engine.eval(new InputStreamReader(in));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// End
	}
	
	public void start() throws Exception {
		Thread th = new Thread() {
			public void run() {
				try {
					Manifest mf = eef.getManifest();
					ZipEntry main = eef.getEntry("js/main.js");
					ZipEntry icon = eef.getEntry("res/icon.png");
					if (icon != null) {
						EEFRunner.this.icon = ImageIO.read(eef.getInputStream(icon));
					}
					runJS(main);
					eef.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.setName("EEF-" + file);
		th.start();
	}

	EEFRunner(EEFFile file) throws IOException {
		this.eef = file;
	}
}