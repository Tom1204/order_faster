package com.example.iut.finalproject.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.Database.Model.OrderItem;

@Database(entities = {OrderItem.class}, version = 2
)
public abstract class LocalDb extends RoomDatabase {
    public abstract ItemDao getItemDao();
}
