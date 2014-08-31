package com.coltsoftware.brainfuckgenetics;

import java.util.Dictionary;
import java.util.Hashtable;

public final class AttemptCache {

	private final Dictionary<String, ProgramScore> attempts = new Hashtable<String, ProgramScore>();

	public ProgramScore previousScore(String program) {
		return attempts.get(program);
	}

	public void saveScore(ProgramScore score) {
		attempts.put(score.getProgram().source(), score);
	}

}
