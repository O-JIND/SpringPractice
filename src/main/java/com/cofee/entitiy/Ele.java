package com.cofee.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Ele {
    private int id;
    private String name;
    private int price;
    private String category;
    private int stock;
    private String description;

    public Ele() {}

    public Ele(int id, String name, int price, String category, int stock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.description = description;
    }
}
