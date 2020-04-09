package ru.natiel.week1homework;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChargeModel that = (ChargeModel) o;
		return price == that.price &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price);
	}
}
