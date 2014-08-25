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
		setupGame(20);
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

}
