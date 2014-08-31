package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.*;

import org.junit.Test;

public final class JoustingScoreTests extends JoustTestsBase {

	@Test
	public void off_tape_1() {
		assertEquals(-1, joust("(>)*50", "+[.]"));
	}

	@Test
	public void off_tape_2() {
		assertEquals(1, joust("+[.]", "(>)*50"));
	}

	@Test
	public void both_off_tape_draw() {
		assertEquals(0, joust("(>)*50", "(>)*50"));
	}

}
