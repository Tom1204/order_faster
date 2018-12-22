package com.example.iut.finalproject.manage.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderItemView;
import com.example.iut.finalproject.models.ManagerOrderItem;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderItemListFragment extends Fragment implements OrderItemView.OnOrderIsDoneListener {

    @BindView(R.id.order_item_placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    private List<ManagerOrderItem> orderItems;
    private OrderItemView.OnOrderItemStatusChanged onOrderItemStatusChanged;

    public static OrderItemListFragment newInstance(List<ManagerOrderItem> orderItems, OrderItemView.OnOrderItemStatusChanged onOrderItemStatusChanged) {
        OrderItemListFragment fragment = new OrderItemListFragment();
        fragment.setOrderItems(orderItems);
        fragment.setOnOrderItemStatusChanged(onOrderItemStatusChanged);
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

    private void addViews(List<ManagerOrderItem> list) {
        for (ManagerOrderItem item : list) {
            placeHolderView.addView(new OrderItemView(getContext(), item, this, this.onOrderItemStatusChanged));
        }
    }

    public List<ManagerOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ManagerOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public void onOrderItemIsDone(OrderItemView view) {

    }

    public void setOnOrderItemStatusChanged(OrderItemView.OnOrderItemStatusChanged onOrderItemStatusChanged) {
        this.onOrderItemStatusChanged = onOrderItemStatusChanged;
    }

}
