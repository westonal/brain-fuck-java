package com.coltsoftware;

public class ProgramPointer {

	private String execString;
	private int pointer;

	public ProgramPointer(String execString) {
		this.execString = execString;
	}

	public boolean atEnd() {
		return pointer >= execString.length();
	}

	public char get() {
		return execString.charAt(pointer++);
	}

	public int getPosition() {
		return pointer;
	}

	public void setPosition(int postion) {
		pointer = postion;
	}

}
