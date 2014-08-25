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

	public void execute(int stepCount) {
		for (int step = 0; step < stepCount; step++)
			if (!executeSingleStep())
				break;
	}

	public boolean executeSingleStep() {
		if (progPointer.atEnd())
			return false;
		Instruction instruction = progPointer.getInstructionAndMovePointer();
		instruction.execute(pointer, progPointer);
		return true;
	}

}
