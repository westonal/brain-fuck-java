package com.coltsoftware.brainfuck;

public final class ProgramPointer {

	private int pointer;
	private Program program;

	public ProgramPointer(Program program) {
		this.program = program;
	}

	public boolean atEnd() {
		return pointer >= program.length();
	}

	public Instruction getInstructionAndMovePointer() {
		return program.instructionAt(pointer++);
	}

	public int getPosition() {
		return pointer;
	}

	public void jumpTo(int postion) {
		pointer = postion;
	}

	public void jumpToAfter(Instruction instruction) {
		jumpTo(instruction.getProgramOffset() + 1);
	}

}
