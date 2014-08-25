package com.coltsoftware.brainfuck;

public abstract class TapePointer {

	public abstract void incValue();

	public abstract void decValue();

	public abstract void inc();

	public abstract void dec();

	public abstract boolean isZero();

	public abstract char asAscii();

	public static TapePointer newForTape(Tape tape) {
		return new StandardTapePointer(tape);
	}

	public static TapePointer reverseForTape(Tape tape) {
		return ReverseTapePointer.reverse(tape);
	}

}
