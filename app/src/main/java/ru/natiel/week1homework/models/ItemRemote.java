package ru.natiel.week1homework.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRemote {
    private String id;
    private String name;
    private Integer price;
    private String type;
    private String date;
}
