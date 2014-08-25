package com.coltsoftware;

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
		Instruction instruction = progPointer.get();
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
				int end = instruction.getClose().getProgramOffset();
				progPointer.setPosition(end + 1);
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
				progPointer.setPosition(pos);
			}
		}
		return true;
	}
}
