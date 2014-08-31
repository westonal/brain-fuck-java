package com.coltsoftware.brainfuck;

public final class StandardTapePointer extends TapePointer {

	private final Tape tape;
	private int position;

	public StandardTapePointer(Tape tape) {
		this.tape = tape;
	}

	public StandardTapePointer(Tape tape, int initialPosition) {
		this(tape);
		position = initialPosition;
	}

	public void incValue() {
		tape.incAtPosition(position);
	}

	public void decValue() {
		tape.decAtPosition(position);
	}

	public void inc() {
		position++;
		if (position >= tape.length())
			throw new TapeException();
	}

	public void dec() {
		position--;
		if (position < 0)
			throw new TapeException();
	}

	public boolean isZero() {
		return tape.getAt(position) == 0;
	}

	public char asAscii() {
		return (char) tape.getAt(position);
	}

}
