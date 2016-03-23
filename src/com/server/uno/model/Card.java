package com.server.uno.model;

public class Card {

	private volatile String color;
	private volatile int number;

	public Card(String color, int number) {
		setColor(color);
		setNumber(number);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		if (!color.equals("red") || !color.equals("yellow") || !color.equals("green") || !color.equals("blue")
				|| !color.equals("black"))
			throw new IllegalArgumentException("Wrong card color: " + color);
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if (number < 0 && number > 14) // TODO Mb number is to big?
			throw new IllegalArgumentException("Wrong card number: " + number);
		this.number = number;
	}

}
