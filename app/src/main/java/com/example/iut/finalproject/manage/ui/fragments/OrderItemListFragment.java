package com.example.iut.finalproject.manage.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderItemView;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.models.OrderItemRead;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderItemListFragment extends Fragment implements OrderItemView.OnOrderIsDoneListener {

    @BindView(R.id.order_item_placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    private List<OrderItemRead> orderItems;

    public static OrderItemListFragment newInstance(List<OrderItemRead> orderItems) {
        OrderItemListFragment fragment = new OrderItemListFragment();
        fragment.setOrderItems(orderItems);
        return fragment;
    }

    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences("UserAuth", getContext().MODE_PRIVATE);
        ButterKnife.bind(this, view);
        addViews(orderItems);
    }

    private void addViews(List<OrderItemRead> list) {
        for (OrderItemRead item : list) {
            placeHolderView.addView(new OrderItemView(getContext(), item, this));
        }
    }

    public List<OrderItemRead> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRead> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public void onOrderItemIsDone(OrderItemView view) {

    }
}
