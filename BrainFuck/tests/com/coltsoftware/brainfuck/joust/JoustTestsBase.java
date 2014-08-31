package com.coltsoftware.brainfuck.joust;

import org.junit.Before;

import com.coltsoftware.brainfuck.joust.Arena.AllLengthScore;
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
		return pitProgramsFull(program1, program2).basicScore();
	}

	protected AllLengthScore pitProgramsFull(String program1, String program2) {
		return builder.programStrings(program1, program2).allLengthScore();
	}

	protected int joust(String program1, String program2) {
		return builder.programStrings(program1, program2).build().joust(10000)
				.getWinner();
	}

}