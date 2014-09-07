brain-fuck-java
===============

TDD Brain fuck engine written in java.

jousting
========

Example usage of the arena:

    JoustResult joust = new Arena.Builder().programStrings("[>[-]]", "[>>[-]]").tapeLength(15).build().joust(10000);

You can pre-compile bots, which expands shorthand and pre-matches brackets for faster execution.

    Program bot1 = Program.compile("[>[-]]");
    Program bot2 = Program.compile("[>>[-]]");		
    JoustResult joust = new Arena.Builder().program1(bot1).program2(bot2).tapeLength(15).build().joust(10000);
		
You can find out from the `JoustResult` who won, in how many moves and even the high-water mark in the bot, which is the furthest placed instruction that executed (isnt accurate with shorthand yet).
