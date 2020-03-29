package ru.natiel.week1homework;

public class ChargeModel {
	
	private String name;
	private int price;
	
	public ChargeModel(final String name, final int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(final int price) {
		this.price = price;
	}
}