package com.coltsoftware;

public class Main {

	public static void main(String[] args) {
		out("Enter Brain Fuck:");		
		BrainFuck brainFuck = new BrainFuck(args[0]);
	}

	private static void out(String string) {
		System.out.print(string);
	}

}
