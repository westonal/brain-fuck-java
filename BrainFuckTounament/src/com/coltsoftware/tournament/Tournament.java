package com.coltsoftware.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.coltsoftware.brainfuck.joust.Arena;
import com.coltsoftware.brainfuck.joust.Arena.JoustResult;
import com.coltsoftware.brainfuck.joust.Bot;

public class Tournament {

	private final Random rand = new Random();

	public final class Score {

		private final Bot bot;

		public Score(Bot bot) {
			this.bot = bot;
		}

		private int points;

		public void win() {
			points++;
		}

		public int getPoints() {
			return points;
		}

		public Bot getBot() {
			return bot;
		}
	}

	private final List<Bot> bots;
	private HashMap<String, Score> scores = new HashMap<String, Score>();

	public Tournament(List<Bot> bots) {
		this.bots = bots;
	}

	public void run() {
		createHashMap();
		int size = bots.size();
		outln(String.format("%d bots to battle", size));
		for (int a = 0; a < size - 1; a++)
			for (int b = a + 1; b < size; b++) {
				Bot bota = bots.get(a);
				Bot botb = bots.get(b);
				out(String.format("%s vs. %s = ", bota.getName(),
						botb.getName()));
				outln(vs(bota, botb));
			}
		outln("");
		outln("----Scores----");
		List<Score> sortedScores = sortScores();
		printScores(sortedScores);
	}

	private void printScores(List<Score> sortedScores) {
		for (Score s : sortedScores)
			outln(String.format("%s = %d", s.getBot().getName(), s.getPoints()));
	}

	private List<Score> sortScores() {
		ArrayList<Score> arrayList = new ArrayList<Score>(scores.values());
		Collections.sort(arrayList, new Comparator<Score>() {
			@Override
			public int compare(Score o1, Score o2) {
				return o2.getPoints() - o1.getPoints();
			}
		});
		return arrayList;
	}

	private String vs(Bot bota, Bot botb) {
		Score botaScore = scores.get(bota.getName());
		Score botbScore = scores.get(botb.getName());
		int localScoreA = 0;
		int localScoreB = 0;
		for (int i = 0; i < 10; i++) {
			JoustResult joust = new Arena.Builder().program1(bota.getProgram())
					.program2(botb.getProgram())
					.tapeLength(10 + rand.nextInt(21)).build().joust(10000);
			switch (joust.getWinner()) {
			case 1:
				botaScore.win();
				localScoreA++;
				break;
			case -1:
				botbScore.win();
				localScoreB++;
				break;
			}
		}
		return String.format("%d:%d", localScoreA, localScoreB);
	}

	private void createHashMap() {
		for (Bot b : bots)
			scores.put(b.getName(), new Score(b));
	}

	private static void out(String string) {
		System.out.print(string);
	}

	private static void outln(String string) {
		System.out.print(string);
		System.out.print("\n");
	}
}
