package com.example.iut.finalproject.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.example.iut.finalproject.ui.fragments.PageFragment;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Category;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.utils.ViewPageAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    public ViewPager viewPager;

    @BindView(R.id.viewpagertab)
    public SmartTabLayout viewPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ButterKnife.bind(this);

        getCategories();
    }

    private void initViewPager(List<Category> list) {
        FragmentPagerItems.Creator items = FragmentPagerItems.with(this);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());


        for (Category category : list) {
            adapter.addFragment(PageFragment.newInstance(category.getId()), category.getName());
        }


        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setAdapter(adapter);

        viewPagerTab.setViewPager(viewPager);
    }

    private void getCategories() {
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        Call<ArrayResponse<Category>> call = service.getCategories();
        call.enqueue(new Callback<ArrayResponse<Category>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayResponse<Category>> call, @NonNull Response<ArrayResponse<Category>> response) {
                initViewPager(response.body().list);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayResponse<Category>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
