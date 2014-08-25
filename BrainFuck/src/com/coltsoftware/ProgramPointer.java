package com.coltsoftware;

public final class ProgramPointer {

	private int pointer;
	private Program program;

	public ProgramPointer(Program program) {
		this.program = program;
	}

	public boolean atEnd() {
		return pointer >= program.length();
	}

	public Instruction get() {
		return program.instructionAt(pointer++);
	}

	public int getPosition() {
		return pointer;
	}

	public void setPosition(int postion) {
		pointer = postion;
	}

}
