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

	private int id;
	private String name;
	private String price;

	public ChargeModel(ItemRemote itemRemote) {
		this.id = id;
		this.name = itemRemote.getName();
		this.price = itemRemote.getPrice() + " Р";
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
