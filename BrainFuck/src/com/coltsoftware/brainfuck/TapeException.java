package com.coltsoftware.brainfuck;

public final class TapeException extends RuntimeException {

	private static final long serialVersionUID = -7688471818644224897L;

	public TapeException() {
		super("Off tape");
	}

}
