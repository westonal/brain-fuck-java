package com.coltsoftware.brainfuck;

import java.util.Stack;

public final class BrainFuck {

	private final TapePointer pointer;
	private final Stack<Integer> progPointerStack = new Stack<Integer>();
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
			if (pointer.isZero()) {
				progPointer.jumpToAfter(instruction.getClose());
			} else {
				progPointerStack.add(progPointer.getPosition());
			}
			break;
		case ']':
			if (progPointerStack.isEmpty())
				throw new MismatchedBracketsException();
			if (pointer.isZero()) {
				progPointerStack.pop();
			} else {
				int pos = progPointerStack.peek();
				progPointer.jumpTo(pos);
			}
		}
	}
}
