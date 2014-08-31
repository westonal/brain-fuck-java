package com.coltsoftware.brainfuckgenetics;

import java.util.HashSet;

public final class AttemptCache {

	private final HashSet<String> attempts = new HashSet<String>();

	public String previousScore(String program) {
		return attempts.contains(program) ? "" : null;
	}

	public void saveScore(ProgramScore score) {
		attempts.add(score.getProgram().source());
	}

}
