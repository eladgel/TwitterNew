package com.everything.twitter.events;

public class TextChangedEvent {

	private String newText;
	public TextChangedEvent(String text) {
		setNewText(text);
	}

	public void setNewText(String text) {
		this.newText = text;
	}

	public String getNewText() {
		return newText;
	}

	public int getNewTextSize() {
		return newText.length();
	}
}
