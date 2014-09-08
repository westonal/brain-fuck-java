package com.coltsoftware.brainfuck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.brainfuck.BrainFuck;
import com.coltsoftware.brainfuck.Tape;
import com.coltsoftware.brainfuck.TapePointer;

public final class PreprocessorTests {

	@Test
	public void nothing_to_pre_compile() {
		Program compiled = Program.compile("+");
		assertEquals(1, compiled.length());
	}

	@Test
	public void single_digit_no_repeat() {
		String preprocessed = new PreProcessor("(+)*1").getResult();
		assertEquals("+", preprocessed);
	}

	@Test
	public void double_digit_repeat() {
		String preprocessed = new PreProcessor("(+)*12").getResult();
		assertEquals("++++++++++++", preprocessed);
	}

	@Test
	public void single_digit_repeat() {
		Program compiled = Program.compile("(+)*4");
		assertEquals(4, compiled.length());
	}

	@Test
	public void double_digit_complex_repeat() {
		Program compiled = Program.compile("(++)*23");
		assertEquals(46, compiled.length());
	}

	@Test(expected = MismatchedBracketsException.class)
	public void bad_brackets() {
		Program.compile("++)*23");
	}

	@Test(expected = MismatchedBracketsException.class)
	public void bad_brackets_2() {
		Program.compile("(++*23");
	}

	@Test(expected = PreProcessorException.class)
	public void symbol_before_times() {
		Program.compile("(++)+*23");
	}

	@Test(expected = PreProcessorException.class)
	public void symbol_after_times() {
		Program.compile("(++)*+23");
	}

	@Test(expected = PreProcessorException.class)
	public void no_times() {
		Program.compile("(++)23");
	}

	@Test
	public void concatenated() {
		Program compiled = Program.compile("(+)*4(-)*3");
		assertEquals(7, compiled.length());
	}

	@Test
	public void nested() {
		String preprocessed = new PreProcessor("(+(-)*2)*4").getResult();
		assertEquals("+--+--+--+--", preprocessed);
	}
}
