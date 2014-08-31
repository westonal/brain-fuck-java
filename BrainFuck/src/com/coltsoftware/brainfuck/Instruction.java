package com.coltsoftware.brainfuck;

public final class Instruction {

	private final char instructionChar;
	private final int programStringOffset;
	private final int programOffset;
	private Instruction matchingBracket;

	public Instruction(char instructionChar, int programStringOffset,
			int programOffset) {
		this.instructionChar = instructionChar;
		this.programStringOffset = programStringOffset;
		this.programOffset = programOffset;
	}

	public Instruction getMatchingBracket() {
		return matchingBracket;
	}

	public void setMatchingBracket(Instruction matchingBracket) {
		this.matchingBracket = matchingBracket;
	}

	public char getInstructionChar() {
		return instructionChar;
	}

	public int getProgramOffset() {
		return programOffset;
	}

	public int getProgramStringOffset() {
		return programStringOffset;
	}

	public void execute(TapePointer pointer, ProgramPointer progPointer) {
		switch (instructionChar) {
		case '.':
			//System.out.print((char) pointer.asAscii());
			break;
		case '+':
			pointer.incValue();
			break;
		case '-':
			pointer.decValue();
			break;
		case '>':
			pointer.inc();
			break;
		case '<':
			pointer.dec();
			break;
		case '[':
			if (pointer.isZero())
				progPointer.jumpToAfter(matchingBracket);
			break;
		case ']':
			if (!pointer.isZero())
				progPointer.jumpToAfter(matchingBracket);
		}
	}
}