package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Test;

public final class OptimizeTests {

	@Test
	public void can_optomise() {
		assertEquals(">", Optomizer.optomise(">+-"));
	}

	@Test
	public void can_optomise_2() {
		assertEquals(">", Optomizer.optomise(">-+"));
	}

	@Test
	public void can_optomise_left_right() {
		assertEquals("+", Optomizer.optomise("+><"));
	}

	@Test
	public void can_optomise_right_left() {
		assertEquals("+", Optomizer.optomise("+<>"));
	}

	@Test
	public void can_optomise_both_left_right_and_minus_plus() {
		assertEquals("", Optomizer.optomise("-<>+"));
	}

	@Test
	public void can_optomise_whole_set() {
		assertEquals("-", Optomizer.optomise(">>><<<-"));
	}

	@Test
	public void doesnt_optimize() {
		assertEquals(">>>-<<<", Optomizer.optomise(">>>-<<<"));
	}

	@Test
	public void does_optimize() {
		assertEquals(">>>-<<<", Optomizer.optomise(">><>>-<<<"));
	}

	@Test
	public void does_optimize_2() {
		assertEquals("?+", Optomizer.optomise("?>><>>+-<<<+"));
	}

}
