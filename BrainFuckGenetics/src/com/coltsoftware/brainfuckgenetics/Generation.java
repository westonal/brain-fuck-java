package com.coltsoftware.brainfuckgenetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.joust.Arena;
import com.coltsoftware.brainfuck.joust.Arena.AllLengthScore;

public class Generation {
	private final HashSet<String> programs = new HashSet<String>();

	private final int MAX_PROG_LENGTH = 1024;

	public void add(String program) {
		int length = program.length();
		if (length == 0)
			return;
		if (length > MAX_PROG_LENGTH)
			programs.add(program.substring(0, MAX_PROG_LENGTH));
		else
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
				Program program = Program.compileOptomized(programSource);
				compiledPrograms.add(program);
			} catch (Exception ex) {
				// System.out.print("Failed to compile source " +
				// programSource);
				// System.out.print("\n");
			}
		}
		return compiledPrograms;
	}

	public List<ProgramScore> scoreGeneration(List<Program> theEnvironment) {
		List<Program> genPrograms = compileGeneration();
		AllLengthScore[] scores = new AllLengthScore[genPrograms.size()];
		for (int i = 0; i < genPrograms.size(); i++) {
			scores[i] = new AllLengthScore();
			Program p = genPrograms.get(i);
			for (Program bot : theEnvironment) {
				scores[i].combine(pit(p, bot));
			}
		}

		List<ProgramScore> progScores = assignScores(genPrograms, scores);

		for (ProgramScore score : progScores) {
			String source = score.getProgram().source();
			int length = source.length();
			if (length > 50) {
				System.out.print(String.format(
						"%s is the score for prog with leng %s\n",
						score.getScore(), length));
			} else {
				System.out.print(String.format("%s is the score for prog %s\n",
						score.getScore(), source));
			}
		}

		return progScores;
	}

	private static List<ProgramScore> assignScores(List<Program> genPrograms,
			AllLengthScore[] scores) {
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
				AllLengthScore score1 = o1.getScore();
				AllLengthScore score2 = o2.getScore();

				final int winsCompare = score2.getLengthsWon()
						- score1.getLengthsWon();
				if (winsCompare != 0)
					return winsCompare;

				final int basicCompare = score2.basicScore()
						- score1.basicScore();
				if (basicCompare != 0)
					return basicCompare;

				final int lostCompare = score2.getLostMoves()
						- score1.getLostMoves();
				if (lostCompare != 0)
					return lostCompare;

				final int drawCompare = score2.getDrawnMoves()
						- score1.getDrawnMoves();
				if (drawCompare != 0)
					return drawCompare;

				return 0;
			}
		});
	}

	private AllLengthScore pit(Program p, Program bot) {
		return new Arena.Builder().program1(p).program2(bot).allLengthScore();
	}

	public int size() {
		return programs.size();
	}

}
