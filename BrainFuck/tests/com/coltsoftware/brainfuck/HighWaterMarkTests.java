package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class HighWaterMarkTests {

	private Tape tape;
	private TapePointer regPointer;

	@Before
	public void setup() {
		tape = new Tape(10);
		regPointer = new StandardTapePointer(tape);
	}

	@Test
	public void initial_high_water() {
		BrainFuck brainFuck = new BrainFuck(regPointer, ".");
		assertNull(brainFuck.getHighestInstruction());
	}

	@Test
	public void initial_last_instruction() {
		BrainFuck brainFuck = new BrainFuck(regPointer, ".");
		assertNull(brainFuck.getLastInstruction());
	}

	@Test
	public void simple_program_high_waater() {
		BrainFuck brainFuck = new BrainFuck(regPointer, ".");
		brainFuck.execute();
		assertEquals(0, brainFuck.getHighestInstruction().getProgramOffset());
	}

	@Test
	public void simple_program_last_instruction() {
		BrainFuck brainFuck = new BrainFuck(regPointer, ".");
		brainFuck.execute();
		assertEquals(0, brainFuck.getLastInstruction().getProgramOffset());
	}

	@Test
	public void longer_program_high_waater() {
		BrainFuck brainFuck = new BrainFuck(regPointer, "..");
		brainFuck.execute();
		assertEquals(1, brainFuck.getHighestInstruction().getProgramOffset());
	}

	@Test
	public void longer_program_last_instruction() {
		BrainFuck brainFuck = new BrainFuck(regPointer, "..");
		brainFuck.execute();
		assertEquals(1, brainFuck.getLastInstruction().getProgramOffset());
	}

	@Test
	public void simple_loop_prog() {
		BrainFuck brainFuck = new BrainFuck(regPointer, "+[-]");
		brainFuck.execute();
		assertEquals(3, brainFuck.getHighestInstruction().getProgramOffset());
		assertEquals(3, brainFuck.getLastInstruction().getProgramOffset());
	}

	@Test
	public void simple_loop_prog_stepped() {
		BrainFuck brainFuck = new BrainFuck(regPointer, "++[-]");
		brainFuck.execute(6);
		assertEquals(4, brainFuck.getHighestInstruction().getProgramOffset());
		assertEquals(3, brainFuck.getLastInstruction().getProgramOffset());
	}
}
