package com.coltsoftware.brainfuck;

public final class ReverseTapePointer extends TapePointer {

	private TapePointer other;

	private ReverseTapePointer(TapePointer other) {
		this.other = other;
	}

	public void incValue() {
		other.incValue();
	}

	public void decValue() {
		other.decValue();
	}

	public void inc() {
		other.dec();
	}

	public void dec() {
		other.inc();
	}

	public boolean isZero() {
		return other.isZero();
	}

	public char asAscii() {
		return other.asAscii();
	}

	public static ReverseTapePointer reverse(Tape tape) {
		return new ReverseTapePointer(new StandardTapePointer(tape,
				tape.length() - 1));
	}

}
