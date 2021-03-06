package com.example.iut.finalproject.Database.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(OrderItem user);

    @Update
    void update(OrderItem user);

    @Delete
    void delete(OrderItem user);

    @Query("select order_item.id, order_item.item, order_item.comment, sum(order_item.count) as count from order_item group by item")
    List<OrderItem> allOrderItem();

    @Query("delete from order_item")
    void clear();

    @Query("select item from order_item group by item")
    List<Integer> allItems();

    @Query("delete from order_item where order_item.item = :itemId")
    void deleteByItem(int itemId);
}
