package com.coltsoftware.brainfuckgenetics;

import java.util.ArrayList;
import java.util.List;

import com.coltsoftware.brainfuck.Program;

public final class Main {

	public static void main(String[] args) {
		List<Program> bots = loadBots();
		new Genetics(bots).go();
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
