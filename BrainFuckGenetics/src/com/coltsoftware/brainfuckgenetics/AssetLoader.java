package com.coltsoftware.brainfuckgenetics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public final class AssetLoader {

	private AssetLoader() {
	}

	public static String[] listBots() {
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

	public static String loadBotString(String botName) {
		String assetName = "bots/" + botName;
		InputStream stream = loadAssetStream(assetName);
		String botString = convertStreamToString(stream);
		return botString;
	}

	private static InputStream loadAssetStream(String assetName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(assetName);
		return stream;
	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner scanner = new java.util.Scanner(is);
		java.util.Scanner s = scanner;
		try {
			return s.hasNext() ? s.next() : "";
		} finally {
			scanner.close();
		}
	}
}
