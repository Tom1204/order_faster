package com.example.iut.finalproject.client.ui.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.fragments.PageFragment;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Category;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;

import java.util.ArrayList;
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
    public TabLayout viewPagerTab;

    private ItemDao itemDao;
    private SharedPreferences sharedPreferences;
    FoodPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
        itemDao = Room.
                databaseBuilder(this, LocalDb.class, "local_db").
                fallbackToDestructiveMigration().
                allowMainThreadQueries().
                build().getItemDao();
        adapter = new FoodPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPagerTab.setupWithViewPager(viewPager);
        getCategories();
    }

    private void initViewPager(List<Category> list) {
        for (Category category : list) {
            adapter.add(PageFragment.newInstance(category.getId()), category.getName());
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout_action:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                itemDao.clear();
                intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.order_list:
                intent = new Intent(this, ClientOrderActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

class FoodPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public FoodPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
