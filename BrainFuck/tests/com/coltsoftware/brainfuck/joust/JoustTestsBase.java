package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.ReverseTapePointer;
import com.coltsoftware.brainfuck.StandardTapePointer;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.TapeException;
import com.coltsoftware.brainfuck.TapePointer;

public abstract class JoustTestsBase extends BotLoadingBase {

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

	protected int pitPrograms(String program1, String program2) {
		int score = 0;
		for (int i = 10; i <= 30; i++) {
			setupGame(i);
			score += joust(program1, program2);
			System.out.print(tape.toString());
			System.out.print("\n");
		}
		return score;
	}

	protected int joust(String program1, String program2) {
		BrainFuck engine1 = new BrainFuck(pointer, program1);
		BrainFuck engine2 = new BrainFuck(pointerreversed, program2);
		int zeroCount1 = 0;
		int zeroCount2 = 0;
		for (int i = 0; i < 10000; i++) {
			boolean out1 = false;
			boolean out2 = false;
			try {
				engine1.executeSingleStep();
			} catch (TapeException ex) {
				System.out.print("Prog 1 end of tape:\n");
				out1 = true;
			}
			try {
				engine2.executeSingleStep();
			} catch (TapeException ex) {
				System.out.print("Prog 2 end of tape:\n");
				out2 = true;
			}
			if (out1 && out2) {
				System.out.print("Draw both out:\n");
				return 0;
			}
			if (out1) {
				return -1;
			}
			if (out2) {
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