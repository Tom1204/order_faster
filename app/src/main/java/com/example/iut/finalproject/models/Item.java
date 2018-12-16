package com.example.iut.finalproject.models;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private String ingredients;

    @SerializedName("price")
    private double price;

    @SerializedName("time_preparation")
    private int timePreparation;

    @SerializedName("is_active")
    private boolean is_active;

    @SerializedName("category")
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTimePreparation() {
        return timePreparation;
    }

    public void setTimePreparation(int timePreparation) {
        this.timePreparation = timePreparation;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
