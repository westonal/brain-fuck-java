package com.coltsoftware;

public final class BrainFuck {

	private TapePointer pointer;

	private String execString;

	public BrainFuck(String execString) {
		this.execString = execString;
	}

	public void execute(Tape tape) {
		pointer = new TapePointer(tape);
		execute(execString);
	}

	private void execute(String partString) {
		ProgramPointer progPointer = new ProgramPointer(partString);
		while (!progPointer.atEnd()) {
			char c = progPointer.get();
			switch (c) {
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
				int position = progPointer.getPosition();
				int end = findEndPosition(partString, position);
				String loop = partString.substring(position, end);
				while (!pointer.isZero())
					execute(loop);
				progPointer.setPosition(end + 1);
				break;
			case ']':
				throw new MismatchedBracketsException();
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
