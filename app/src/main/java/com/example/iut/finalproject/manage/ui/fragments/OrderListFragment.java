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
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderView;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Order;
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
public class OrderListFragment extends Fragment implements OrderView.OnOrderIsDoneListener {

    @BindView(R.id.order_placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences("UserAuth", getContext().MODE_PRIVATE);
        ButterKnife.bind(this, view);
        getItems();
    }

    private void getItems() {
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
        Call<ArrayResponse<Order>> call = service.getOrderByStatus(auth, status);
        call.enqueue(new Callback<ArrayResponse<Order>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayResponse<Order>> call, @NonNull Response<ArrayResponse<Order>> response) {
                if (response.isSuccessful())
                    addViews(response.body().list);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayResponse<Order>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addViews(List<Order> list) {
        for (Order item : list) {
            placeHolderView.addView(new OrderView(getContext(), item, this));
        }
    }

    static public OrderListFragment newInstance(String status) {
        OrderListFragment fragment = new OrderListFragment();
        fragment.setStatus(status);
        return fragment;
    }

    @Override
    public void onOrderIsDone(OrderView view) {
        placeHolderView.removeView(view);
    }
}
