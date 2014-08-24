package com.coltsoftware;

public final class Tape {

	private final byte[] tapeContents;

	public Tape(int length) {
		tapeContents = new byte[length];
	}

	public boolean isAllZero() {
		return true;
	}

	public byte getAt(int position) {
		return tapeContents[position];
	}

	public void incAtPosition(int position) {
		tapeContents[position]++;
	}

	public void decAtPosition(int position) {
		tapeContents[position]--;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte b : tapeContents)
			sb.append(" " + b);
		return sb.toString();
	}

}
