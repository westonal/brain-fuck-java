package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.*;

import org.junit.Test;

public class FullJoustTests extends JoustTestsBase {

	@Test
	public void can_open_all_bots() {
		assertTrue(joustProgramAgainstBot(">(->)*9(+[-]>)*20", "Alternator.bf") > 0);
	}

	private int joustProgramAgainstBot(String string, String botFileName) {
		String bot = loadBotString(botFileName);
		int joust = pitPrograms(string, bot);	
		System.out.print(String.format("Score: %d\n", joust));
		return joust;
	}
}
