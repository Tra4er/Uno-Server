package com.server.uno.model;

public class Card implements Cloneable {

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
		if (!color.equals("red") && !color.equals("yellow") && !color.equals("green") && !color.equals("blue")
				&& !color.equals("black"))
			throw new IllegalArgumentException("Wrong card color: " + color);
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if (number < 0 && number > 14)
			throw new IllegalArgumentException("Wrong card number: " + number);
		this.number = number;
	}

	@Override
	public String toString() {
		return "Card [color=" + color + ", number=" + number + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + number;
		return result;
	}

	@Override
	public Card clone() throws CloneNotSupportedException {
		return (Card) super.clone();
	}
}
