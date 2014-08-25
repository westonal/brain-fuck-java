package com.coltsoftware.brainfuck;

public final class BrainFuck {

	private final TapePointer pointer;
	private final ProgramPointer progPointer;

	public BrainFuck(TapePointer pointer, String execString) {
		this.pointer = pointer;
		progPointer = new ProgramPointer(Program.compile(execString));
	}

	public void execute() {
		while (executeSingleStep())
			;
	}

	public boolean executeSingleStep() {
		if (progPointer.atEnd())
			return false;
		exectuteInstruction(progPointer.getInstructionAndMovePointer());
		return true;
	}

	private void exectuteInstruction(Instruction instruction) {
		char c = instruction.getInstructionChar();
		switch (c) {
		case '.':
			System.out.print((char) pointer.asAscii());
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
				progPointer.jumpToAfter(instruction.getMatchingBracket());
			break;
		case ']':
			if (!pointer.isZero())
				progPointer.jumpToAfter(instruction.getMatchingBracket());
		}
	}
}
