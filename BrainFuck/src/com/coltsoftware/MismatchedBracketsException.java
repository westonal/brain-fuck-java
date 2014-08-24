package com.coltsoftware;

public final class MismatchedBracketsException extends RuntimeException {

	private static final long serialVersionUID = -3070356957214908137L;

	public MismatchedBracketsException() {
		super("Mismatched []");
	}

}
