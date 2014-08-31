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

	private final Random rand;
	private Generation generation;
	private final List<Program> theEnvironment;

	public Genetics(List<Program> theEnvironment) {
		this.theEnvironment = theEnvironment;
		rand = new Random(435987);
	}

	public void go() {
		out("GO");
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
			if (generationNumber % 100 == 0)
				saveGeneration(scoreGeneration, generationNumber);

			Generation next = createNextGeneration(scoreGeneration,
					Math.min(2048, 256 * (generationNumber / 8000 + 1)));
			setCurrentGeneration(next);
			generationNumber++;
		}
	}

	private static void saveGeneration(List<ProgramScore> scoreGeneration,
			int generationNumber) {
		try {
			File logs = new File("logs");
			if (!logs.exists())
				logs.mkdir();
			File outFile = new File(logs, String.format("Generation%d.txt",
					generationNumber));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void out(String string) {
		System.out.print(string);
		System.out.print("\n");
	}

	private Generation createNextGeneration(List<ProgramScore> scoreGeneration,
			int maxLength) {
		Generation generation = new Generation(maxLength);
		while (generation.size() < 20 && !scoreGeneration.isEmpty()) {
			ProgramScore topScore = scoreGeneration.remove(0);
			generation.add(topScore);

			String source = topScore.getProgram().source();
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, mutate(source));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 1));
			addCheckingHistory(generation, grow(source, 2));
			addCheckingHistory(generation, shrinkRandom(source, 1));
			addCheckingHistory(generation, shrinkRandom(source, 2));
			addCheckingHistory(
					generation,
					breed(source,
							scoreGeneration
									.get(rand.nextInt(scoreGeneration.size()))
									.getProgram().source()));
		}
		return generation;
	}

	private String shrinkRandom(String source, int bitsToRemove) {
		StringBuilder sb = new StringBuilder(source);
		for (int i = 1; i < bitsToRemove; i++)
			sb.deleteCharAt(rand.nextInt(sb.length()));
		return sb.toString();
	}

	private String shrink(String source, int n) {
		return source.substring(n);
	}

	private void addCheckingHistory(Generation generation, String source) {
		generation.add(source);
	}

	private String grow(String source, int bitsToInsert) {
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
		if (rand.nextInt(10) == 0) {
			return mutateAddLoop(source);
		}

		int bitsToMutate = rand.nextInt(5);
		char[] charArray = source.toCharArray();
		for (int i = 1; i < bitsToMutate; i++) {
			int toReplace2 = -1;
			int toReplace = rand.nextInt(charArray.length);
			char c = charArray[toReplace];
			if (c == '[') {
				toReplace2 = source.indexOf(']', toReplace);
			}
			if (c == ']') {
				toReplace2 = source.indexOf('[');
			}
			charArray[toReplace] = randomCharater();
			if (toReplace2 != -1)
				charArray[toReplace2] = randomCharater();
		}
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
		return allChars[rand.nextInt(allChars.length)];
	}
}
