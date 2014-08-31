package com.coltsoftware.brainfuckgenetics;

import java.util.List;
import java.util.Random;

import com.coltsoftware.brainfuck.Program;

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
			out(String.format("Generation %d (%d)", generationNumber,
					generation.size()));
			long time = System.currentTimeMillis();
			List<ProgramScore> scoreGeneration = generation
					.scoreGeneration(theEnvironment);
			out(String.format("Generation %d took %d ms", generationNumber,
					System.currentTimeMillis() - time));

			Generation next = createNextGeneration(scoreGeneration);
			setCurrentGeneration(next);
			generationNumber++;
		}
	}

	private void out(String string) {
		System.out.print(string);
		System.out.print("\n");
	}

	private Generation createNextGeneration(List<ProgramScore> scoreGeneration) {
		Generation generation = new Generation();
		while (generation.size() < 40 && !scoreGeneration.isEmpty()) {
			ProgramScore topScore = scoreGeneration.remove(0);
			String source = topScore.getProgram().source();
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
		int bitsToInsert = rand.nextInt(9) + 1;
		StringBuilder sb = new StringBuilder(source);
		for (int i = 1; i < bitsToInsert; i++)
			sb.insert(rand.nextInt(sb.length()), randomCharater());
		return sb.toString();
	}

	private String breed(String source1, String source2) {
		if (source2.length() == 0)
			throw new RuntimeException();
		int placeToChop1 = rand.nextInt(source1.length()) + 1;
		int placeToChop2 = rand.nextInt(source2.length() - 1);
		return source1.substring(0, placeToChop1)
				+ source2.substring(placeToChop2);
	}

	private String mutate(String source) {
		if (rand.nextInt(20) == 0) {
			return mutateAddLoop(source);
		}

		int bitsToMutate = rand.nextInt(5);
		char[] charArray = source.toCharArray();
		for (int i = 1; i < bitsToMutate; i++)
			charArray[rand.nextInt(charArray.length)] = randomCharater();
		return new String(charArray);
	}

	private String mutateAddLoop(String source) {
		if (rand.nextInt(20) == 0) {
			return mutateAddLoop(source);
		}
		StringBuilder sb = new StringBuilder(source);
		sb.insert(rand.nextInt(source.length() - 1), getRandomLoop());
		return sb.toString();
	}

	private String[] allLoops = new String[] { "[-]", "[+]" };

	private String getRandomLoop() {
		return allLoops[rand.nextInt(allLoops.length)];
	}

	private void setCurrentGeneration(Generation generation) {
		this.generation = generation;
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
