package com.coltsoftware.brainfuckgenetics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coltsoftware.brainfuck.Program;
import com.coltsoftware.brainfuck.joust.BotFileLoader;

public final class Main {

	private final static Random rand = new Random(9541);

	public static void main(String[] args) {
		List<Program> bots = new BotFileLoader("bots").loadBotsPrograms();

		if (args.length > 0) {
			Genetics genetics = new Genetics(4291, bots);
			genetics.setFirstGen(loadGeneration(args[0]),
					getGenerationNumber(args[0]));
			genetics.go();
		} else {
			List<Genetics> populations = createPopulations(bots, 20);
			int size = populations.size();
			while (true) {
				for (int i = 0; i < size; i++) {
					final Genetics population = populations.get(i);
					population.goOne(populations.get(
							rand.nextInt(populations.size())).randomProgram(
							rand));
				}
				System.out.print("");
			}
		}
	}

	private static List<Genetics> createPopulations(List<Program> bots, int n) {
		ArrayList<Genetics> arrayList = new ArrayList<Genetics>();
		arrayList.add(new Genetics(4291, bots));
		for (int i = 1; i < n; i++) {
			int seed = i * 100;
			arrayList.add(new Genetics(seed, bots));
		}
		return arrayList;
	}

	private static int getGenerationNumber(String string) {
		Matcher matcher = Pattern.compile("(\\d+)(?!.*\\d)").matcher(string);
		matcher.find();
		return Integer.parseInt(matcher.group());
	}

	private static Generation loadGeneration(String string) {

		ArrayList<String> allstrings = loadAllStrings(string);

		Generation generation = new Generation(10000);

		for (int i = 0; i < allstrings.size(); i++) {
			String s = allstrings.get(i);
			if (s.equals("RawCode:")) {
				String program = allstrings.get(i + 1);
				System.out.print(program);
				System.out.print("\n");
				generation.add(program);
			}
		}

		return generation;
	}

	private static ArrayList<String> loadAllStrings(String fileName) {
		ArrayList<String> allstrings = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				allstrings.add(line);
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allstrings;
	}

}
