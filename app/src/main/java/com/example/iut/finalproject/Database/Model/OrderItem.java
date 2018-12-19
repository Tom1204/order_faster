package com.example.iut.finalproject.Database.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "order_item")
public class OrderItem {

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
}
