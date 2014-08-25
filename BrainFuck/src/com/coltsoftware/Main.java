package com.coltsoftware;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.TapePointer;

public class Main {

	public static void main(String[] args) {
		out("Enter Brain Fuck:");
		new BrainFuck(new TapePointer(new Tape(20)), args[0]).execute();
	}

	private static void out(String string) {
		System.out.print(string);
	}

}
