package com.coltsoftware.brainfuckgenetics;

import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.joust.Arena.AllLengthScore;

public final class ProgramScore {
	private final Program program;
	private final AllLengthScore score;

	public ProgramScore(Program program, AllLengthScore score) {
		this.program = program;
		this.score = score;
	}

	public AllLengthScore getScore() {
		return score;
	}

	public Program getProgram() {
		return program;
	}
}