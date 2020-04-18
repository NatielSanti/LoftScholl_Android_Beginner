package ru.natiel.week1homework.screen.adapter;

import ru.natiel.week1homework.model.ChargeModel;

public interface ChargeModelAdapterListener {
    public void onItemClick(ChargeModel item, int position);

    public void onItemLongClick(ChargeModel item, int position);
}
