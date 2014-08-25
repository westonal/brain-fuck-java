package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.TapePointer;

public class BrainFuckJoustTests {

	private Tape tape;
	private TapePointer pointer;
	private TapePointer pointerreversed;
	private String setTo128 = "(+)*127";

	@Before
	public void setup() {
		setupGame(30);
		pointer = new StandardTapePointer(tape);
		pointerreversed = ReverseTapePointer.reverse(tape);
	}

	private void setupGame(int tapeLength) {
		tape = new Tape(tapeLength);
		TapePointer reg = new StandardTapePointer(tape);
		TapePointer rev = ReverseTapePointer.reverse(tape);
		new BrainFuck(reg, setTo128).execute();
		new BrainFuck(rev, setTo128).execute();
		assertEquals(127, tape.getAt(0));
		assertEquals(127, tape.getAt(tapeLength - 1));
		for (int i = 1; i < tapeLength - 1; i++)
			assertEquals(0, tape.getAt(i));
	}

	private void execute(String execString) {
		new BrainFuck(pointer, execString).execute();
	}

	private void executeSteps(String execString, int stepCount) {
		BrainFuck brainFuck = new BrainFuck(pointer, execString);
		brainFuck.execute(stepCount);
	}

	@Test
	public void alternator() {
		executeSteps("(>+>-)*4>+(>[-][.])*21", 100);
		// assertEquals(1, tape.getAt(0));
		System.out.print(tape.toString());
	}

	@Test
	public void two() {
		executeSteps("(>->->+)*3([-]>)*11", 1000);
		// assertEquals(1, tape.getAt(0));
		assertEquals("", tape.toString());
		System.out.print(tape.toString());
	}

	@Test
	public void pit() {
		String program1 = "(>+>-)*4>+(>[-][.])*21";
		String program2 = "(>)*19(>[-])*11";
		BrainFuck engine1 = new BrainFuck(pointer, program1);
		BrainFuck engine2 = new BrainFuck(pointerreversed, program2);
		int zeroCount1 = 0;
		int zeroCount2 = 0;
		for (int i = 0; i < 10000; i++) {
			engine1.executeSingleStep();
			engine2.executeSingleStep();
			if (tape.getAt(0) == 0)
				zeroCount1++;
			else
				zeroCount1 = 0;
			if (tape.getAt(tape.length() - 1) == 0)
				zeroCount2++;
			else
				zeroCount2 = 0;
			if (zeroCount1 == 2) {
				System.out.print("Player 1 dead");
				break;
			}
			if (zeroCount2 == 2) {
				System.out.print("Player 2 dead");
				break;
			}
		}
		System.out.print("End");
	}

}
