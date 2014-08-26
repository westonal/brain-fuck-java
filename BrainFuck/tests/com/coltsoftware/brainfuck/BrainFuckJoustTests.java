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

	@Test
	public void two() {
		executeSteps("(>->->+)*3([-]>)*11", 1000);
		// assertEquals(1, tape.getAt(0));
		assertEquals("", tape.toString());
		System.out.print(tape.toString());
	}

	private final static String ALTERNATOR = "(>+>-)*4>+(>[-][.])*21";
	private final static String DECOYBOT = ">(+)*10>(-)*10(>+>-)*3(>[-].)*21";
	private final static String COUNTERPUNCH = "+>(-)*12(>)*7(<(-)*12<(+)*12)*3(>)*7(([-([(-)*6[+].])*5])*4>)*21";

	private final static String MY_V2 = "(>-)*9(+[-]>)*20";

	@Test
	public void pit_against_alternator() {
		String program1 = ALTERNATOR;
		// String program2 = "(>)*19(>[-][.])*11";
		String program2 = "(>)*9(+[-]>)*20";
		int score = pitPrograms(program1, program2);
		assertEquals(1, score);
		System.out.print("End");
	}

	@Test
	public void version_2_pit_against_alternator() {
		String program1 = ALTERNATOR;
		String program2 = MY_V2;
		int score = pitPrograms(program1, program2);
		assertEquals(1, score);
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
		assertEquals(1, score);
		System.out.print("End");
	}
	
	@Test
	public void version_2_pit_against_counter_punch() {
		String program1 = COUNTERPUNCH;
		String program2 = MY_V2;
		int score = pitPrograms(program1, program2);
		assertEquals(1, score);
		System.out.print("End");
	}

	private int pitPrograms(String program1, String program2) {
		int score = 0;
		for (int i = 10; i <= 30; i++) {
			setupGame(i);
			score += joust(program1, program2);
			System.out.print(tape.toString());
			System.out.print("\n");
		}
		return score;
	}

	private int joust(String program1, String program2) {
		BrainFuck engine1 = new BrainFuck(pointer, program1);
		BrainFuck engine2 = new BrainFuck(pointerreversed, program2);
		int zeroCount1 = 0;
		int zeroCount2 = 0;
		for (int i = 0; i < 10000; i++) {
			try {
				engine1.executeSingleStep();
			} catch (Exception ex) {
				System.out.print("Prog 1 end of tape:\n");
				return -1;
			}
			try {
				engine2.executeSingleStep();
			} catch (Exception ex) {
				System.out.print("Prog 2 end of tape:\n");
				return 1;
			}
			if (tape.getAt(0) == 0)
				zeroCount1++;
			else
				zeroCount1 = 0;
			if (tape.getAt(tape.length() - 1) == 0)
				zeroCount2++;
			else
				zeroCount2 = 0;
			if (zeroCount1 == 2 && zeroCount2 == 2) {
				System.out.print("Draw by death:\n");
				return 0;
			}
			if (zeroCount1 == 2) {
				System.out.print("Prog 2 clasic win:\n");
				return -1;
			}
			if (zeroCount2 == 2) {
				System.out.print("Prog 1 clasic win:\n");
				return 1;
			}
		}
		System.out.print("Draw:\n");
		return 0;
	}

}
