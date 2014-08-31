package com.coltsoftware.brainfuck.joust;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public class BotLoadingBase {

	protected static String[] listBots() {
		try {
			ArrayList<String> result = new ArrayList<String>();
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			Enumeration<URL> resources = classLoader.getResources("bots");
			while (resources.hasMoreElements()) {
				URL nextElement = resources.nextElement();
				File path = new File(nextElement.toURI());
				for (File files : path.listFiles())
					result.add(files.getName());
			}
			return result.toArray(new String[result.size()]);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	protected static String loadBotString(String botName) {
		String assetName = "bots/" + botName;
		InputStream stream = loadAssetStream(assetName);
		String botString;
		try {
			botString = fromStream(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return botString;
	}

	private static InputStream loadAssetStream(String assetName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(assetName);
		return stream;
	}

	private static String fromStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
			out.append(newLine);
		}
		return out.toString();
	}
}