package com.example.iut.finalproject.Database.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "order_item")
public class OrderItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "item")
    @SerializedName("item")
    private int item;

    @ColumnInfo(name = "count")
    @SerializedName("count")
    private int count;

    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    private String comment;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;


    @ColumnInfo(name = "total_price")
    @SerializedName("total_price")
    private String totalPrice;


    @Ignore
    public OrderItem(int item, int count, String comment) {
        this.item = item;
        this.count = count;
        this.comment = comment;
    }

    public OrderItem(int item, int count) {
        this.item = item;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
