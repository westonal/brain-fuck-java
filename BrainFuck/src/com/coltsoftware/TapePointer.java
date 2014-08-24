package com.coltsoftware;

public final class TapePointer {

	private final Tape bfTape;
	private int position;

	public TapePointer(Tape bfTape) {
		this.bfTape = bfTape;
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

}
