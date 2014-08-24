package com.coltsoftware;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BrainFuckTests {

	private Tape tape;

	@Before
	public void setup() {
		tape = new Tape(20);
	}

	@Test
	public void do_nothing() {
		execute(".");
		assertTapeEmpty();
	}

	private void execute(String execString) {
		BrainFuck brainFuck = new BrainFuck(execString);
		brainFuck.execute(tape);
	}

	@Test
	public void add_one() {
		execute("+");
		assertEquals(1, tape.getAt(0));
	}

	@Test
	public void add_one_twice() {
		execute("++");
		assertEquals(2, tape.getAt(0));
	}

	@Test
	public void sub_one() {
		execute("-");
		assertEquals(-1, tape.getAt(0));
	}

	@Test
	public void move_and_add_one() {
		execute(">+");
		assertEquals(1, tape.getAt(1));
	}

	@Test
	public void move_and_sub_one() {
		execute(">-");
		assertEquals(-1, tape.getAt(1));
	}

	@Test
	public void move_right_and_add_one() {
		execute(">><+");
		assertEquals(1, tape.getAt(1));
	}

	private void assertTapeEmpty() {
		assertTrue(tape.isAllZero());
	}

	@Test
	public void empty_while_does_nothing() {
		execute("[]+");
		assertEquals(1, tape.getAt(0));
	}

	@Test
	public void while_can_be_used_to_clear() {
		execute("+[-]");
		assertEquals(0, tape.getAt(0));
	}

	@Test
	public void while_can_be_used_to_clear_2() {
		execute("++[-]");
		assertEquals(0, tape.getAt(0));
	}

	@Test
	public void nested_whiles() {
		execute("++>++>++");
		assertEquals(2, tape.getAt(0));
		assertEquals(2, tape.getAt(1));
		assertEquals(2, tape.getAt(2));
		assertEquals(0, tape.getAt(3));
		execute("[[-]>]");
		assertEquals(0, tape.getAt(0));
		assertEquals(0, tape.getAt(1));
		assertEquals(0, tape.getAt(2));
		assertEquals(0, tape.getAt(3));
	}

	@Test(expected = MismatchedBracketsException.class)
	public void mismatch() {
		execute("++[-");
	}

	@Test(expected = MismatchedBracketsException.class)
	public void mismatch_2() {
		execute("[[-]");
	}

	@Test(expected = MismatchedBracketsException.class)
	public void mismatch_3() {
		execute("[-]]");
	}

	@Test
	public void hello_world() {
		execute("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.");
	}

	@Test
	public void alan() {
		execute("++++[>" +
				"++++[>" +
				"++[>" +
				"++[>" +
				"+" +
				">+" +
				">+" + 
				">+<<<" + 
				"<-]"+
				">>+>+>+<<<<" +
				"<-]" +
				">>>+>>+<<<<<" +
				"<-]" +
				"<-]" +
				">>>>+.>----.>+.>--.");
		//System.out.print(tape.toString());
		assertEquals(65, tape.getAt(4));
		assertEquals(108, tape.getAt(5));
		assertEquals(97, tape.getAt(6));
		assertEquals(110, tape.getAt(7));
	}
	
	@Test
	public void alan_one_line() {
		execute("++++[>++++[>++[>++[>+>+>+>+<<<<-]>>+>+>+<<<<<-]>>>+>>+<<<<<<-]<-]>>>>+.>----.>+.>--.");
		//System.out.print(tape.toString());
		assertEquals(65, tape.getAt(4));
		assertEquals(108, tape.getAt(5));
		assertEquals(97, tape.getAt(6));
		assertEquals(110, tape.getAt(7));
	}

}
