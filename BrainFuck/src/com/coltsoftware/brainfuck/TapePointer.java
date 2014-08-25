package com.coltsoftware.brainfuck;

public final class TapePointer {

	private final Tape bfTape;
	private int position;

	public TapePointer(Tape tape) {
		this.bfTape = tape;
	}

	public void incValue() {
		bfTape.incAtPosition(position);
	}

	public void decValue() {
		bfTape.decAtPosition(position);
	}

	public void inc() {
		position++;
	}

	public void dec() {
		position--;
	}

	public boolean isZero() {
		return bfTape.getAt(position) == 0;
	}

	public char asAscii() {
		return (char) bfTape.getAt(position);
	}

}
