package com.coltsoftware.brainfuckgenetics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import com.coltsoftware.brainfuck.Optomizer;
import com.coltsoftware.brainfuck.Program;

public final class Genetics {

	private final String name;
	private final Random rand;
	private Generation generation;
	private final List<Program> theEnvironment;
	private final AttemptCache cache = new AttemptCache();

	public Genetics(String name, int seed, List<Program> theEnvironment) {
		this.name = name;
		this.theEnvironment = theEnvironment;
		rand = new Random(seed);
	}

	public Genetics(int seed, List<Program> theEnvironment) {
		this(String.format("Seed%d", seed), seed, theEnvironment);
	}

	private int generationNumber = 0;
	private Generation firstGeneration;

	public void go() {
		out("GO");

		setCurrentGeneration(firstGeneration);

		while (true)
			goOne(null);
	}

	public void goOne(String additional) {
		if (firstGeneration == null) {
			firstGeneration = createFirstGeneration();
			setCurrentGeneration(firstGeneration);
		}
		out(String.format("%s Generation %d (%d)", name, generationNumber,
				generation.size()));
		long time = System.currentTimeMillis();
		List<ProgramScore> scoreGeneration = generation
				.scoreGeneration(theEnvironment);
		out(String.format("Generation %d took %d ms", generationNumber,
				System.currentTimeMillis() - time));
		if (generationNumber % 100 == 0)
			saveGeneration(scoreGeneration, generationNumber);

		writeToCache(scoreGeneration);

		Generation next = generationNumber >= 12000
				&& generationNumber % 1000 == 0 ? createNextSuperGeneration(
				scoreGeneration, 2048) : createNextGeneration(scoreGeneration,
				additional, 2048);

		setCurrentGeneration(next);
		generationNumber++;
	}

	private void writeToCache(List<ProgramScore> scoreGeneration) {
		for (ProgramScore score : scoreGeneration)
			cache.saveScore(score);
	}

	private void saveGeneration(List<ProgramScore> scoreGeneration,
			int generationNumber) {
		try {
			File logs = new File("logs");
			if (!logs.exists())
				logs.mkdir();
			File outFile = new File(logs, String.format("%sGeneration%d.txt",
					name, generationNumber));
			FileOutputStream fileOutputStream = new FileOutputStream(outFile,
					false);
			PrintStream printStream = new PrintStream(fileOutputStream, true,
					"UTF-8");
			printStream.format("Generation %d\n", generationNumber);
			for (ProgramScore score : scoreGeneration) {
				printStream.format("Score: %s\n", score.getScore());
				printStream.format("RawCode:\n%s\n\n", score.getProgram()
						.source());

				printStream.format("TightCode:\n%s\n\n",
						Optomizer.optomize(score.getProgram().source()));
			}
			printStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void out(String string) {
		System.out.print(string);
		System.out.print("\n");
	}

	private Generation createNextGeneration(List<ProgramScore> scoreGeneration,
			String additional, int maxLength) {
		Generation generation = new Generation(maxLength);
		while (generation.size() < 40 && !scoreGeneration.isEmpty()) {
			ProgramScore topScore = scoreGeneration.remove(0);
			generation.add(topScore);

			String source = topScore.getProgram().source();

			if (topScore.getScore().getHighWater1() == null)
				continue;

			int trimAt = topScore.getScore().getHighWater1()
					.getProgramStringOffset();

			source = source.substring(0, trimAt);

			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 2));
			addCheckingHistory(generation, shrinkRandom(source, 1));
			addCheckingHistory(generation, shrinkRandom(source, 2));
			if (scoreGeneration.size() > 0)
				addCheckingHistory(
						generation,
						breed(source,
								scoreGeneration
										.get(safeRand(scoreGeneration.size()))
										.getProgram().source()));
			if (additional != null)
				addCheckingHistory(generation, breed(source, additional));
		}
		return generation;
	}

	private Generation createNextSuperGeneration(
			List<ProgramScore> scoreGeneration, int maxLength) {
		Generation generation = new Generation(maxLength);

		for (ProgramScore s : scoreGeneration)
			generation.add(s);

		while (generation.size() < 2000) {
			ProgramScore topScore = scoreGeneration.get(rand
					.nextInt(scoreGeneration.size()));
			String source = topScore.getProgram().source();

			int trimAt = topScore.getScore().getHighWater1()
					.getProgramStringOffset();

			source = source.substring(0, trimAt);

			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 2));
			addCheckingHistory(generation, shrinkRandom(source, 1));
			addCheckingHistory(generation, shrinkRandom(source, 2));
			if (scoreGeneration.size() > 0)
				addCheckingHistory(
						generation,
						breed(source,
								scoreGeneration
										.get(safeRand(scoreGeneration.size()))
										.getProgram().source()));
		}
		return generation;
	}

	private String shrinkRandom(String source, int bitsToRemove) {
		StringBuilder sb = new StringBuilder(source);
		for (int i = 1; i < bitsToRemove; i++) {
			if (sb.length() == 0)
				break;
			sb.deleteCharAt(safeRand(sb.length()));
		}
		return sb.toString();
	}

	private String shrink(String source, int n) {
		return source.substring(n);
	}

	private void addCheckingHistory(Generation generation, String source) {
		if (cache.previousScore(source) != null)
			return;
		generation.add(source);
	}

	private String grow(String source, int bitsToInsert) {
		StringBuilder sb = new StringBuilder(source);
		for (int i = 1; i < bitsToInsert; i++)
			sb.insert(safeRand(sb.length()), randomCharater());
		return sb.toString();
	}

	private int safeRand(int nonInclusiveMax) {
		if (nonInclusiveMax == 0)
			return 0;
		return rand.nextInt(nonInclusiveMax);
	}

	private String breed(String source1, String source2) {
		if (source2.length() == 0)
			throw new RuntimeException();
		if (source2.length() < 2)
			return source1;
		if (source1.length() < 2)
			return source2;
		int placeToChop1 = safeRand(source1.length()) + 1;
		int placeToChop2 = safeRand(source2.length() - 1);
		return source1.substring(0, placeToChop1)
				+ source2.substring(placeToChop2);
	}

	private String mutate(String source) {
		if (safeRand(10) == 0 || source.length() < 2) {
			return mutateAddLoop(source);
		}

		int bitsToMutate = safeRand(5);
		char[] charArray = source.toCharArray();
		for (int i = 1; i < bitsToMutate; i++) {
			int toReplace2 = -1;
			int toReplace = safeRand(charArray.length);
			char c = charArray[toReplace];
			if (c == '[') {
				toReplace2 = source.indexOf(']', toReplace);
			}
			if (c == ']') {
				toReplace2 = indexOfReverse(source, '[', toReplace);
			}
			charArray[toReplace] = randomCharater();
			if (toReplace2 != -1)
				charArray[toReplace2] = randomCharater();
		}
		return new String(charArray);
	}

	private static int indexOfReverse(String source, char c, int toReplace) {
		char[] chars = source.toCharArray();
		for (int i = toReplace; i >= 0; i--)
			if (chars[i] == c)
				return i;
		return -1;
	}

	private String mutateAddLoop(String source) {
		if (source.length() <= 2)
			return source + getRandomLoop();

		StringBuilder sb = new StringBuilder(source);
		sb.insert(safeRand(source.length() - 1), getRandomLoop());
		return sb.toString();
	}

	private String[] allLoops = new String[] { "[-]", "[+]" };

	private String getRandomLoop() {
		return allLoops[safeRand(allLoops.length)];
	}

	private void setCurrentGeneration(Generation generation) {
		this.generation = generation;
	}

	private Generation createFirstGeneration() {
		Generation generation = new Generation(10);
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
		return allChars[safeRand(allChars.length)];
	}

	public void setFirstGen(Generation generation, int genNumber) {
		firstGeneration = generation;
		generationNumber = genNumber;
	}

	public String randomProgram(Random rand) {
		if (generation != null)
			return generation.getProgram(safeRand(generation.size()));
		return null;
	}
}
