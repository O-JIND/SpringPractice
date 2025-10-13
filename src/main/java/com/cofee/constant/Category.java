package com.cofee.constant;

public enum Category {
    ALL("전체"), BREAD("빵"), BEVERAGE("음료"), CAKE("케이크");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
