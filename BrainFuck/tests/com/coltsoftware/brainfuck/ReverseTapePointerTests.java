package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class ReverseTapePointerTests {

	private Tape tape;
	private TapePointer regPointer;
	private TapePointer reversePointer;

	@Before
	public void setup() {
		tape = new Tape(10);
		regPointer = new StandardTapePointer(tape);
		reversePointer = ReverseTapePointer.reverse(tape);
	}

	@Test
	public void initial_position() {
		reversePointer.incValue();
		assertEquals(1, tape.getAt(9));
	}

	@Test
	public void inc_moves_left() {
		reversePointer.inc();
		reversePointer.incValue();
		assertEquals(1, tape.getAt(8));
	}

	@Test
	public void dec_moves_right() {
		reversePointer.inc();
		reversePointer.inc();
		reversePointer.inc();
		reversePointer.dec();
		reversePointer.incValue();
		assertEquals(1, tape.getAt(7));
	}

	@Test
	public void works_on_same_tape() {
		regPointer.inc();
		regPointer.incValue();
		reversePointer.inc();
		reversePointer.incValue();
		assertEquals(1, tape.getAt(1));
		assertEquals(1, tape.getAt(8));
	}

}
