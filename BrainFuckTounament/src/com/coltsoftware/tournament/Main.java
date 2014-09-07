package com.coltsoftware.tournament;

import java.util.List;

import com.coltsoftware.brainfuck.joust.Bot;
import com.coltsoftware.brainfuck.joust.BotFileLoader;

public final class Main {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		List<Bot> bots = new BotFileLoader("bots").loadBots();
		new Tournament(bots).run();
		outln(String.format("Tounament complete in %d ms",
				System.currentTimeMillis() - time));
	}

	private static void out(String string) {
		System.out.print(string);
	}

	private static void outln(String string) {
		out(string);
		System.out.print("\n");
	}
}
