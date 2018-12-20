package com.example.iut.finalproject.manage.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderItemView;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.OrderItemRead;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderItemFragment extends Fragment implements OrderItemView.OnOrderItemIsDoneListener{

    @BindView(R.id.order_item_placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    private String status;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences("UserAuth", getContext().MODE_PRIVATE);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            status = getArguments().getString("status");
        getItems();
    }

    private void getItems() {
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
        Call<ArrayResponse<OrderItemRead>> call = service.getOrderItemsByStatus(auth, status);
        call.enqueue(new Callback<ArrayResponse<OrderItemRead>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayResponse<OrderItemRead>> call, @NonNull Response<ArrayResponse<OrderItemRead>> response) {
                addViews(response.body().list);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayResponse<OrderItemRead>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addViews(List<OrderItemRead> list) {
        for (OrderItemRead item : list) {
            placeHolderView.addView(new OrderItemView(getContext(), item, this));
        }
    }

    static public OrderItemFragment newInstance(String status) {
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        OrderItemFragment fragment = new OrderItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onOrderItemIsDone(OrderItemView view) {
        placeHolderView.removeView(view);
    }
}
