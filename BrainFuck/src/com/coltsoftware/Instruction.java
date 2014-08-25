package com.coltsoftware;

public final class Instruction {

	private final char instructionChar;
	private final int programStringOffset;
	private final int programOffset;
	private Instruction close;
	private Instruction open;

	public Instruction(char instructionChar, int programStringOffset,
			int programOffset) {
		this.instructionChar = instructionChar;
		this.programStringOffset = programStringOffset;
		this.programOffset = programOffset;
	}

	public char getInstructionChar() {
		return instructionChar;
	}

	void setOpen(Instruction open) {
		this.open = open;
	}

	void setClose(Instruction close) {
		this.close = close;
	}

	public Instruction getClose() {
		return close;
	}

	public Instruction getOpen() {
		return open;
	}

	public int getProgramOffset() {
		return programOffset;
	}

	public int getProgramStringOffset() {
		return programStringOffset;
	}
}