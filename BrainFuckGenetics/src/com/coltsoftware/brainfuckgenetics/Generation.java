package com.coltsoftware.brainfuckgenetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.joust.Arena;

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
				System.out.print("Failed to compile source " + programSource);
				System.out.print("\n");
			}
		}
		return compiledPrograms;
	}

	public List<ProgramScore> scoreGeneration(List<Program> theEnvironment) {
		List<Program> genPrograms = compileGeneration();
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

	private static List<ProgramScore> assignScores(List<Program> genPrograms,
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
				return o2.getScore() - o1.getScore();
			}
		});
	}

	private int pit(Program p, Program bot) {
		return new Arena.Builder().program1(p).program2(bot).allLengthScore();
	}

}
