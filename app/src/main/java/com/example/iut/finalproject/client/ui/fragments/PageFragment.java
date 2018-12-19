package com.example.iut.finalproject.client.ui.fragments;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.CartActivity;
import com.example.iut.finalproject.client.ui.activities.Login;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Item;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.client.ui.placeholderViews.FoodItemView;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private int categoryId;
    private LocalDb localDb;

    @BindView(R.id.placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            categoryId = getArguments().getInt("categoryId");
        localDb = Room.
                databaseBuilder(getContext(), LocalDb.class, "local_db").
                fallbackToDestructiveMigration().
                build();
        getItems();
    }

    private void getItems() {
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        Call<ArrayResponse<Item>> call = service.getItems(categoryId);
        call.enqueue(new Callback<ArrayResponse<Item>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayResponse<Item>> call, @NonNull Response<ArrayResponse<Item>> response) {
                addViews(response.body().list);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayResponse<Item>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addViews(List<Item> list) {
        for (Item item : list) {
            placeHolderView.addView(new FoodItemView(item, getContext()));
        }
    }

    static public PageFragment newInstance(int categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.fab_add_to_card)
    public void makeOrder(View view) {
        Intent intent = new Intent(getContext(), CartActivity.class);
        startActivity(intent);
    }
}
