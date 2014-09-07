package com.coltsoftware.brainfuck.joust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.coltsoftware.brainfuck.Program;

public final class BotFileLoader {

	private final File botFolder;

	public BotFileLoader(File botFolder) {
		this.botFolder = botFolder;
	}

	public BotFileLoader(String botFolder) {
		this(new File(botFolder));
	}

	public String[] listBots() {
		File[] files = botFolder.listFiles();
		ArrayList<String> list = new ArrayList<String>();
		for (File f : files)
			list.add(f.getName());
		return list.toArray(new String[list.size()]);
	}

	public String loadBotString(String botName) {
		File fileName = new File(botFolder, botName);
		return loadAllToString(fileName);
	}

	private String loadAllToString(File fileName) {
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

	public List<Bot> loadBots() {
		ArrayList<Bot> bots = new ArrayList<Bot>();
		for (String botName : listBots()) {
			System.out.print(botName);
			System.out.print("\n");
			try {
				Program bot = Program.compile(loadBotString(botName));
				bots.add(new Bot(bot, botName.replace(".bf", "")));
			} catch (Exception ex) {
				System.out.print("Failed to compile bot named " + botName);
				ex.printStackTrace();
				System.out.print("\n");
			}
		}
		return bots;
	}

	public List<Program> loadBotsPrograms() {
		List<Bot> bots = loadBots();
		List<Program> programs = new ArrayList<Program>();
		for (Bot b : bots)
			programs.add(b.getProgram());
		return programs;
	}
}
