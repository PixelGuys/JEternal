package org.jeternal.internal.eef;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ESLModule {

	private ArrayList<String> exportedClasses;
	
	public ESLModule(InputStream is) {
		Scanner sc = new Scanner(is);
		while (sc.hasNextLine()) {
			String l = sc.nextLine();
			if (l.startsWith("exports ")) {
				String i = l.replace("exports ", "") + ".js";
				exportedClasses.add(i);
			}
		}
		sc.close();
	}
	
	public String[] getExported() {
		return exportedClasses.toArray(new String[exportedClasses.size()]);
	}
	
}
