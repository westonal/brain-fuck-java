package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.brainfuck.Optomizer;
import com.coltsoftware.brainfuck.joust.Arena.AllLengthScore;

public class ConfirmationTests extends JoustTestsBase {

	@Test
	public void against_B_B() {
		String p1 = Optomizer.optomize("><>->><>+->>->++<+>+-<-+<<<<>>->");
		String bb = loadBotString("BurlyBalderV2.bf");
		assertEquals(-21, pitPrograms(p1, bb));
	}

	@Test
	public void against_B_B_2() {
		String p1 = Optomizer.optomize("++++[+]<<-+<->++>-<<<<+>+<--+><->+++>>+<<>-<-<");
		String bb = loadBotString("BurlyBalderV2.bf");
		AllLengthScore score = pitProgramsFull(p1, bb);
		assertEquals(-21, score.basicScore());
	}
	
	
}
