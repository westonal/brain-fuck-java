package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Test;

public final class ProgramTests {

	@Test
	public void get_back_empty_source() {
		assertCanGetSource("");
	}

	@Test
	public void get_back_other_source() {
		assertCanGetSource(">");
	}

	@Test
	public void get_back_other_source_before_pre_processing() {
		assertCanGetSource("(>)*9");
	}

	private void assertCanGetSource(String source) {
		assertEquals(source, Program.compile(source).source());
	}
}
