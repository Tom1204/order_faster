package com.example.iut.finalproject.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id = 0, tableNumber;
    private Date createdDateTime;
    private Double totalPrice;
    private String status;
    private List<Item> orderedFood;

    public Order(int id, int tableNumber, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderedFood = new ArrayList<>();
    }

    private void generateID() {
        if (this.id == 0) {
            this.id = 1;
        }
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void addFood(Item food){
        this.orderedFood.add(food);
    }
}
