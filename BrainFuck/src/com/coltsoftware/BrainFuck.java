package com.coltsoftware;

import java.util.Stack;

public final class BrainFuck {

	private final TapePointer pointer;
	private final String execString;

	public BrainFuck(TapePointer pointer, String execString) {
		this.pointer = pointer;
		this.execString = execString;
	}

	public void execute() {
		ProgramPointer progPointer = new ProgramPointer(execString);
		Stack<Integer> progPointerStack = new Stack<Integer>();
		while (!progPointer.atEnd()) {
			char c = progPointer.get();
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
					int end = findEndPosition(execString,
							progPointer.getPosition());
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
		}
	}

	private static int findEndPosition(String partString, int position) {
		char[] charArray = partString.toCharArray();
		int open = 1;
		for (int i = position; i < charArray.length; i++) {
			char c = charArray[i];
			switch (c) {
			case '[':
				open++;
				break;
			case ']':
				open--;
				if (open == 0)
					return i;
				break;
			}
		}
		throw new MismatchedBracketsException();
	}
}
