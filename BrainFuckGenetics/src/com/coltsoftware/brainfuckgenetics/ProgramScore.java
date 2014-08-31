package com.coltsoftware.brainfuckgenetics;

import com.coltsoftware.brainfuck.Program;

public class ProgramScore {
	private final Program program;
	private final int score;

	public ProgramScore(Program program, int score) {
		this.program = program;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public Program getProgram() {
		return program;
	}
}