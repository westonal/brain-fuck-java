package com.coltsoftware.brainfuckgenetics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public final class BotFileLoader {

	private BotFileLoader() {
	}

	public static String[] listBots() {
		File path = new File("bots");
		File[] files =path.listFiles();
		ArrayList<String> list=new ArrayList<String>();
		for(File f:files)
			list.add(f.getName());
		return list.toArray(new String[list.size()]);
	}

	protected static String loadBotString(String botName) {
		String fileName = "bots/" + botName;
		return loadAllToString(fileName);
	}
	
	private static String loadAllToString(String fileName) {
		StringBuilder allstrings = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) 
				allstrings.append(line);
			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allstrings.toString();
	}
}
