package com.coltsoftware.brainfuck.joust;

import org.junit.Before;

import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.joust.Arena.Builder;

public abstract class JoustTestsBase extends BotLoadingBase {

	private Builder builder;

	@Before
	public void setup_builder() {
		setupGame(30);
	}

	private void setupGame(int tapeLength) {
		builder = new Arena.Builder().tapeLength(tapeLength);
	}

	protected int pitPrograms(String program1, String program2) {
		int score = 0;
		builder.programStrings(program1, program2);
		for (int i = 10; i <= 30; i++) {
			Arena arena = builder.tapeLength(i).build();
			score += arena.joust(10000).getWinner();
			Tape tape = arena.getTape();
			System.out.print(tape.toString());
			System.out.print("\n");
		}
		return score;
	}

	protected int joust(String program1, String program2) {
		return builder.programStrings(program1, program2).build().joust(10000)
				.getWinner();
	}

}