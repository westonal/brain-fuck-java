package com.coltsoftware.brainfuck.joust;

import com.coltsoftware.brainfuck.Program;

public final class Bot {

	private final Program program;
	private final String name;

	public Bot(Program program, String name) {
		this.program = program;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Program getProgram() {
		return program;
	}

}
