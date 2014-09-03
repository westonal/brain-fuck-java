package com.coltsoftware.brainfuckgenetics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.coltsoftware.brainfuck.Program;

public final class Main {

	public static void main(String[] args) {
		List<Program> bots = loadBots();
		Genetics genetics = new Genetics(bots);
		if (args.length > 0)
			genetics.setFirstGen(loadGeneration(args[0]),
					Integer.parseInt(args[1]));
		genetics.go();
	}

	private static Generation loadGeneration(String string) {

		ArrayList<String> allstrings = loadAllStrings(string);

		Generation generation = new Generation(10000);

		for (int i = 0; i < allstrings.size(); i++) {
			String s = allstrings.get(i);
			if (s.equals("RawCode:")) {
				String program = allstrings.get(i + 1);
				System.out.print(program);
				System.out.print("\n");
				generation.add(program);
			}
		}

		return generation;
	}

	private static ArrayList<String> loadAllStrings(String fileName) {
		ArrayList<String> allstrings = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				allstrings.add(line);
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allstrings;
	}

	private static List<Program> loadBots() {
		ArrayList<Program> bots = new ArrayList<Program>();
		for (String botName : AssetLoader.listBots()) {
			System.out.print(botName);
			System.out.print("\n");
			try {
				Program bot = Program.compile(AssetLoader
						.loadBotString(botName));
				bots.add(bot);
			} catch (Exception ex) {
				System.out.print("Failed to compile bot named " + botName);
				System.out.print("\n");
			}
		}
		return bots;
	}

}
