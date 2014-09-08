package com.coltsoftware.brainfuck;

import java.util.Stack;

public final class PreProcessor {

	private String string;

	public PreProcessor(String string) {
		this.string = process(string);
	}

	public String getResult() {
		return string;
	}

	private static String process(String string) {
		char[] charArray = string.toCharArray();

		Stack<Character> stack = new Stack<Character>();
		for (int i = charArray.length - 1; i >= 0; i--) {
			char c = charArray[i];
			stack.push(c);
		}

		return processStack(stack);
	}

	private static String processStack(Stack<Character> stack) {
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			char c = stack.pop();
			switch (c) {
			case '(':
				processAfterOpenBracket(stack, sb);
				break;
			case ')':
				throw new MismatchedBracketsException();
			default:
				sb.append(c);
				break;
			}
		}

		return sb.toString();
	}

	private static void processAfterOpenBracket(Stack<Character> stack,
			StringBuilder sb) {
		String copyText = getCopyText(stack);
		readStar(stack);
		int n = readNumber(stack);
		if (n == 0)
			throw new PreProcessorException();
		for (int i = 0; i < n; i++)
			sb.append(copyText);
	}

	private static int readNumber(Stack<Character> stack) {
		int value = 0;
		while (!stack.isEmpty()) {
			char peek = stack.peek();
			if (peek >= '0' && peek <= '9') {
				value = value * 10 + (peek - '0');
				stack.pop();
				continue;
			}
			break;
		}
		return value;
	}

	private static void readStar(Stack<Character> stack) {
		if (stack.pop() != '*')
			throw new PreProcessorException();
	}

	private static String getCopyText(Stack<Character> stack) {
		StringBuilder sb = new StringBuilder();
		boolean closed = false;
		while (!stack.isEmpty()) {
			char c = stack.pop();
			if (c == ')') {
				closed = true;
				break;
			}
			if (c == '(') {
				processAfterOpenBracket(stack, sb);
			} else
				sb.append(c);
		}
		if (!closed)
			throw new MismatchedBracketsException();
		return sb.toString();
	}
}
