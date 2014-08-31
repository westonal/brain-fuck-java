package com.coltsoftware.brainfuck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public final class Program implements Iterable<Instruction> {

	private final ArrayList<Instruction> instructions;
	private final String source;

	private Program(ArrayList<Instruction> instructions, String source) {
		this.instructions = instructions;
		this.source = source;
	}

	public static Program compile(final String source) {

		String processedSource = new PreProcessor(source).getResult();

		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		char[] charArray = processedSource.toCharArray();
		int programOffset = 0;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			switch (c) {
			case '+':
			case '-':
			case '.':
			case '>':
			case '<':
			case '[':
			case ']':
				instructions.add(new Instruction(c, i, programOffset++));
			}
		}

		Stack<Instruction> opens = new Stack<Instruction>();
		for (Instruction i : instructions) {
			if (i.getInstructionChar() == '[')
				opens.push(i);
			if (i.getInstructionChar() == ']') {
				if (opens.isEmpty())
					throw new MismatchedBracketsException();
				associateBrackets(opens.pop(), i);
			}
		}
		if (!opens.isEmpty())
			throw new MismatchedBracketsException();

		return new Program(instructions, source);
	}

	private static void associateBrackets(Instruction open, Instruction close) {
		open.setMatchingBracket(close);
		close.setMatchingBracket(open);
	}

	@Override
	public Iterator<Instruction> iterator() {
		return instructions.iterator();
	}

	public Instruction instructionAt(int i) {
		return instructions.get(i);
	}

	public int length() {
		return instructions.size();
	}

	public String source() {
		return source;
	}

}
