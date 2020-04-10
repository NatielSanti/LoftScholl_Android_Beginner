package ru.natiel.week1homework.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeModel {
	
	private String name;
	private String price;

	public ChargeModel(ItemRemote itemRemote) {
		this.name = itemRemote.getName();
		this.price = itemRemote.getPrice() + " Р";
	}

	public ChargeModel(String name, int price) {
		this.name = name;
		this.price = price + " Р";
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
