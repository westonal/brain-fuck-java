package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.ReverseTapePointer;
import com.coltsoftware.brainfuck.StandardTapePointer;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.TapePointer;

public class EarlyBrainFuckJoustTests extends JoustTestsBase{

	private Tape tape;
	private TapePointer pointer;
	private TapePointer pointerreversed;
	private String setTo128 = "(+)*127";

	@Before
	public void setup() {
		setupGame(30);
	}

	private void setupGame(int tapeLength) {
		tape = new Tape(tapeLength);
		pointer = new StandardTapePointer(tape);
		pointerreversed = ReverseTapePointer.reverse(tape);
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

	private final static String ALTERNATOR = "(>+>-)*4>+(>[-][.])*21";
	private final static String DECOYBOT = ">(+)*10>(-)*10(>+>-)*3(>[-].)*21";
	private final static String COUNTERPUNCH = "+>(-)*12(>)*7(<(-)*12<(+)*12)*3(>)*7(([-([(-)*6[+].])*5])*4>)*21";

	private final static String MY_V2 = "(>-)*8>(+[-]>)*20";

	@Test
	public void pit_against_alternator() {
		String program1 = ALTERNATOR;
		// String program2 = "(>)*19(>[-][.])*11";
		String program2 = "(>)*9(+[-]>)*20";
		int score = pitPrograms(program1, program2);
		assertEquals(15, score);
		System.out.print("End");
	}

	@Test
	public void version_2_pit_against_alternator() {
		String program1 = ALTERNATOR;
		String program2 = MY_V2;
		int score = pitPrograms(program1, program2);
		assertEquals(-14, score);
		System.out.print("End");
	}

	@Test
	public void version_2_pit_against_self() {
		String program2 = MY_V2;
		int score = pitPrograms(program2, program2);
		assertEquals(0, score);
		System.out.print("End");
	}

	@Test
	public void version_2_pit_against_decoybot() {
		String program1 = DECOYBOT;
		String program2 = MY_V2;
		int score = pitPrograms(program1, program2);
		assertEquals(-17, score);
		System.out.print("End");
	}
	
	@Test
	public void version_2_pit_against_counter_punch() {
		String program1 = COUNTERPUNCH;
		String program2 = MY_V2;
		int score = pitPrograms(program1, program2);
		assertEquals(19, score);
		System.out.print("End");
	}

}
