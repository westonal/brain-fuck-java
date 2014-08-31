package com.coltsoftware.brainfuckgenetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.joust.Arena;

public final class Genetics {

	private final Random rand;
	private Generation generation;
	private final List<Program> theEnvironment;

	public Genetics(List<Program> theEnvironment) {
		this.theEnvironment = theEnvironment;
		rand = new Random(435987);
	}

	public void go() {
		Generation firstGeneration = createFirstGeneration();
		setCurrentGeneration(firstGeneration);

		long time = System.currentTimeMillis();

		generation.scoreGeneration(theEnvironment);

		System.out.print(String.format("Generation %d took %d ms", 0,
				System.currentTimeMillis() - time));
		System.out.print("\n");

		// new Arena.Builder().
	}

	private void setCurrentGeneration(Generation generation) {
		this.generation = generation;
		generation.printToScreen();
	}

	private Generation createFirstGeneration() {
		Generation generation = new Generation();
		for (int i = 0; i < 20; i++) {
			int length = 10;
			generation.add(generateRandom(length));
		}
		return generation;
	}

	private String generateRandom(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(randomCharater());
		}
		return sb.toString();
	}

	private char[] allChars = new char[] { '>', '<', '+', '-' };

	private char randomCharater() {
		return allChars[rand.nextInt(allChars.length)];
	}
}
