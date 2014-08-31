package com.coltsoftware.brainfuck;

public final class Optomizer {

	private Optomizer() {
	}

	public static String optomize(String string) {
		while (true) {
			String string2 = string.replace("+-", "").replace("-+", "")
					.replace("><", "").replace("<>", "");
			if (string2 == string)
				return string;
			string = string2;
		}
	}

}
