package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.brainfuck.joust.Arena.JoustResult;

public final class ArenaTests {

	@Test
	public void inital_setup_length_10() {
		Arena arena = new Arena.Builder().tapeLength(10)
				.programStrings("[-]", "+[]").build();
		assertEquals(-128, arena.getTape().getAt(0));
		assertEquals(-128, arena.getTape().getAt(9));
	}

	@Test
	public void can_create_correct_length() {
		Arena arena = new Arena.Builder().tapeLength(10)
				.programStrings("(>)*9[-]", "+[]").build();
		JoustResult joustResult = arena.joust(11 + 257);
		System.out.print(arena.getTape());
		assertEquals(1, joustResult.getWinner());
		assertEquals(11 + 257, joustResult.getMoves());
	}

	@Test
	public void only_plays_number_of_moves_given_resulting_in_draw() {
		Arena arena = new Arena.Builder().tapeLength(10)
				.programStrings("(>)*9[-]", "+[]").build();
		JoustResult joust = arena.joust(10);
		assertEquals(0, joust.getWinner());
		assertEquals(10, joust.getMoves());
	}

	@Test
	public void no_double_joust() {
		Arena arena = new Arena.Builder().tapeLength(10)
				.programStrings("(>)*9[-]", "+[]").build();
		JoustResult joust = arena.joust(10);
		assertEquals(0, joust.getWinner());
		assertEquals(10, joust.getMoves());
	}

	@Test
	public void high_water_test() {
		Arena arena = new Arena.Builder().tapeLength(10)
				.programStrings("+[.]", ">+[-]<<[.]").build();
		JoustResult joust = arena.joust(10);
		assertEquals(1, joust.getWinner());
		assertEquals(3, joust.getP1HighInstruction().getProgramOffset());
		assertEquals(6, joust.getP2HighInstruction().getProgramOffset());
	}
}
