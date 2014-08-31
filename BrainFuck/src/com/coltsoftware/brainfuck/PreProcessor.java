package com.coltsoftware.brainfuck;

import java.util.Stack;

public final class PreProcessor {

	private String string;

	public PreProcessor(String string) {
		this.string = string;
		while (containsFirst10K('(') || containsFirst10K(')')
				|| containsFirst10K('*')) {
			String newString = process();
			this.string = newString;
		}
	}

	private boolean containsFirst10K(char c) {
		int indexOf = this.string.indexOf(c);
		return indexOf >= 0 && indexOf <= 10000;
	}

	public String getResult() {
		return string;
	}

	private String process() {
		StringBuilder sb = new StringBuilder(string);

		Stack<Integer> stack = new Stack<Integer>();

		String repeatText = null;
		int i = 0;
		while (i < sb.length()) {
			char c = sb.charAt(i);
			switch (c) {
			case '(': {
				sb.replace(i, i + 1, " ");
				stack.push(i);
				break;
			}
			case ')': {
				sb.replace(i, i + 1, " ");
				if (stack.isEmpty())
					throw new MismatchedBracketsException();
				repeatText = sb.substring(stack.pop() + 1, i);
				break;
			}
			case '*': {
				sb.replace(i, i + 1, " ");
				if (repeatText == null)
					throw new PreProcessorException();
				int repeats = getNumber(sb, i + 1);
				if (repeats <= 0)
					throw new PreProcessorException();
				for (int r = 1; r < repeats; r++)
					sb.insert(i, repeatText);
				break;
			}
			default:
				repeatText = null;
				if (c >= '0' && c <= '9')
					sb.replace(i, i + 1, " ");
				break;
			}
			i++;
			if (i > 10000)
				break;
		}

		i = 0;
		while (i < sb.length()) {
			if (sb.charAt(i) == ' ')
				sb.deleteCharAt(i);
			else
				i++;
		}

		return sb.toString();
	}

	private static int getNumber(StringBuilder sb, int startAt) {
		int value = 0;
		for (int i = startAt; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= '0' && c <= '9')
				value = value * 10 + (c - '0');
			else
				break;
		}
		return value;
	}
}
