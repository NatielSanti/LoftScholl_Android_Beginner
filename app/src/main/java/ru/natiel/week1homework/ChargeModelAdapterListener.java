package ru.natiel.week1homework;

import ru.natiel.week1homework.models.ChargeModel;

public interface ChargeModelAdapterListener {
    public void onItemClick(ChargeModel item, int position);

    public void onItemLongClick(ChargeModel item, int position);
}
