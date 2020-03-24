package ru.natiel.week1homework.screens.main.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeModel implements Serializable {
    public static String KEY_NAME = ChargeModel.class.getName();

    private String name;
    private String value;
}
