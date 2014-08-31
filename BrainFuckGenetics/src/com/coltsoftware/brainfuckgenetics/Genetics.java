package com.coltsoftware.brainfuckgenetics;

import java.util.ArrayList;
import java.util.Random;

import com.coltsoftware.brainfuck.joust.Arena;

public final class Genetics {

	private final Random rand;
	private Generation generation;

	public Genetics() {
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
	}

	public void go() {		
		Generation firstGeneration = createFirstGeneration();
		setCurrentGeneration(firstGeneration);
		
		//new Arena.Builder().
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
		for (int i = 0; i < 10; i++) {
			sb.append(randomCharater());
		}
		return sb.toString();
	}

	private char[] allChars = new char[] { '>', '<', '+', '-' };

	private char randomCharater() {
		return allChars[rand.nextInt(allChars.length)];
	}
}
