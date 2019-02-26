package org.jeternal.internal.eef;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.ScriptException;

public class ESLModule {

	private ArrayList<String> exportedClasses = new ArrayList<>();
	private ESLFile file;
	
	public ESLModule(ESLFile file, InputStream is) {
		Scanner sc = new Scanner(is);
		while (sc.hasNextLine()) {
			String l = sc.nextLine();
			if (l.startsWith("exports ")) {
				String i = l.replace("exports ", "") + ".js";
				exportedClasses.add(i);
			}
		}
		sc.close();
		this.file = file;
	}
	
	public Object getFromClass(String klass) {
		if (exportedClasses.contains(klass + ".js")) {
			String path = "lib/" + klass + ".js";
			try {
				return file.getScriptEngine().eval(new InputStreamReader(file.getInputStream(file.getEntry(path))));
			} catch (ScriptException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String[] getExported() {
		return exportedClasses.toArray(new String[exportedClasses.size()]);
	}
	
}
