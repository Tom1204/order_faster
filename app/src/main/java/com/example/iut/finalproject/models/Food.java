package com.example.iut.finalproject.models;

public class Food {
    private String name, ingredients;
    private double price;
    private int timePreparation;

    public Food(String name, String ingredients, double price, int timePreparation) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.timePreparation = timePreparation;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public double getPrice() {
        return price;
    }

    public int getTimePreparation() {
        return timePreparation;
    }
}
