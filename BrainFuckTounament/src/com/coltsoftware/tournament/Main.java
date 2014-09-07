package com.coltsoftware.tournament;

import java.util.List;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.StandardTapePointer;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.joust.Bot;
import com.coltsoftware.brainfuck.joust.BotFileLoader;

public final class Main {
	public static void main(String[] args) {
		out("Enter Brain Fuck:");
		List<Bot> bots = new BotFileLoader("bots")
				.loadBots();

		new Tournament(bots).run();
		
		
	}

	private static void out(String string) {
		System.out.print(string);
	}

	private static void outln(String string) {
		System.out.print(string);
		System.out.print("\n");
	}
}
