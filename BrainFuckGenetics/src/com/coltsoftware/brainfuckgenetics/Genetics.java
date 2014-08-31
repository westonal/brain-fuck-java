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

		int generationNumber = 0;

		while (true) {
			out(String.format("Generation %d (%d)", generationNumber, generation.size()));
			long time = System.currentTimeMillis();
			List<ProgramScore> scoreGeneration = generation
					.scoreGeneration(theEnvironment);
			out(String.format("Generation %d took %d ms", generationNumber,
					System.currentTimeMillis() - time));

			Generation next = createNextGeneration(scoreGeneration);
			setCurrentGeneration(next);
			generationNumber++;
		}

		// new Arena.Builder().
	}

	private void out(String string) {
		System.out.print(string);
		System.out.print("\n");
	}

	private Generation createNextGeneration(List<ProgramScore> scoreGeneration) {
		Generation generation = new Generation();
		for (int i = 0; i < scoreGeneration.size() / 4; i++) {
			ProgramScore programScore = scoreGeneration.get(i);
			String source = programScore.getProgram().source();
			generation.add(source);
			generation.add(mutate(source));
			generation.add(grow(source));
			generation.add(breed(source,
					scoreGeneration.get(rand.nextInt(scoreGeneration.size()))
							.getProgram().source()));
		}
		return generation;
	}

	private String grow(String source) {
		int bitsToInsert = rand.nextInt(5);
		StringBuilder sb = new StringBuilder(source);
		for (int i = 1; i < bitsToInsert; i++)
			sb.insert(rand.nextInt(sb.length()), randomCharater());
		return sb.toString();
	}

	private String breed(String source1, String source2) {
		int placeToChop1 = rand.nextInt(source1.length() - 1) + 1;
		int placeToChop2 = rand.nextInt(source2.length() - 1) + 1;
		return source1.substring(0, placeToChop1)
				+ source2.substring(placeToChop2);
	}

	private String mutate(String source) {
		int bitsToMutate = rand.nextInt(5);
		char[] charArray = source.toCharArray();
		for (int i = 1; i < bitsToMutate; i++)
			charArray[rand.nextInt(charArray.length)] = randomCharater();
		return new String(charArray);
	}

	private void setCurrentGeneration(Generation generation) {
		this.generation = generation;
		generation.printToScreen();
	}

	private Generation createFirstGeneration() {
		Generation generation = new Generation();
		for (int i = 0; i < 40; i++) {
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
