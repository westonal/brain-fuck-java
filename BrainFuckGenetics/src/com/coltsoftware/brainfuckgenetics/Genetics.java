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

	public class Generation {
		private final ArrayList<String> programs = new ArrayList<String>();

		public void add(String program) {
			programs.add(program);
		}

		public void printToScreen() {
			for (String program : programs) {
				System.out.print(program);
				System.out.print("\n");
			}
		}

		public List<Program> compileGeneration() {
			ArrayList<Program> compiledPrograms = new ArrayList<Program>();
			for (String programSource : programs) {
				try {
					Program program = Program.compile(programSource);
					compiledPrograms.add(program);
				} catch (Exception ex) {
					System.out.print("Failed to compile source "
							+ programSource);
					System.out.print("\n");
				}
			}
			return compiledPrograms;
		}

	}

	public void go() {
		Generation firstGeneration = createFirstGeneration();
		setCurrentGeneration(firstGeneration);

		long time = System.currentTimeMillis();

		scoreGeneration();

		System.out.print(String.format("Generation %d took %d ms", 0,
				System.currentTimeMillis() - time));
		System.out.print("\n");

		// new Arena.Builder().
	}

	public static class ProgramScore {
		private final Program program;
		private final int score;

		public ProgramScore(Program program, int score) {
			this.program = program;
			this.score = score;
		}

		public int getScore() {
			return score;
		}

		public Program getProgram() {
			return program;
		}
	}

	private List<ProgramScore> scoreGeneration() {
		List<Program> genPrograms = generation.compileGeneration();
		int[] scores = new int[genPrograms.size()];
		for (int i = 0; i < genPrograms.size(); i++) {
			Program p = genPrograms.get(i);
			for (Program bot : theEnvironment) {
				scores[i] += pit(p, bot);
			}
		}

		List<ProgramScore> progScores = assignScores(genPrograms, scores);

		for (ProgramScore score : progScores)
			System.out.print(String.format("%d is the score for %s\n",
					score.getScore(), score.getProgram().source()));

		return progScores;
	}

	private List<ProgramScore> assignScores(List<Program> genPrograms,
			int[] scores) {
		List<ProgramScore> progScores = new ArrayList<ProgramScore>();
		for (int j = 0; j < scores.length; j++) {
			progScores.add(new ProgramScore(genPrograms.get(j), scores[j]));
		}
		sortByScores(progScores);
		return progScores;
	}

	private static void sortByScores(List<ProgramScore> progScores) {
		Collections.sort(progScores, new Comparator<ProgramScore>() {
			@Override
			public int compare(ProgramScore o1, ProgramScore o2) {
				return o2.score - o1.score;
			}
		});
	}

	private int pit(Program p, Program bot) {
		return new Arena.Builder().program1(p).program2(bot).allLengthScore();
	}

	private void setCurrentGeneration(Generation generation) {
		this.generation = generation;
		generation.printToScreen();
	}

	private Generation createFirstGeneration() {
		Generation generation = new Generation();
		for (int i = 0; i < 10; i++) {
			int length = rand.nextInt(5) + 5;
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
