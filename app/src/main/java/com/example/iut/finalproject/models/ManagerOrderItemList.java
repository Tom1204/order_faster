package com.example.iut.finalproject.models;

import java.util.ArrayList;

public class ManagerOrderItemList extends ArrayList<ManagerOrderItem> {

    public boolean ready() {
        for (ManagerOrderItem orderItem : this) {
            if (orderItem.getStatus().equals(ManagerOrder.PENDING))
                return false;
        }
        return true;
    }
}
