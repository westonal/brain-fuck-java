package com.coltsoftware.brainfuck.joust;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class FullJoustLoadingTests extends JoustTestsBase {

	@Test
	public void can_load_bot_from_assets() throws IOException,
			URISyntaxException {
		String botString = loadBotString("128Bot.bf");
		assertNotNull(botString);
	}

	@Test
	public void can_list_bots() {
		String[] listBots = listBots();
		assertTrue(listBots.length > 0);
		for (String bot : listBots)
			System.out.print(bot + "\n");
	}

	@Test
	public void can_open_all_bots() {
		for (String bot : listBots())
			assertNotNull(loadBotString(bot));
	}
}
