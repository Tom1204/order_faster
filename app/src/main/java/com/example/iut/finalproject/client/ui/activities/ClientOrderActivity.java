package com.example.iut.finalproject.client.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.fragments.ClientOrderListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientOrderActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @BindView(R.id.orderviewpager)
    public ViewPager viewPager;

    @BindView(R.id.orderviewpagertab)
    public TabLayout viewPagerTab;

    ClientOrderPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        adapter = new ClientOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPagerTab.setupWithViewPager(viewPager);
        initViewPager();
    }

    private void initViewPager() {
        String[] statuses = new String[]{"Pending", "Finished", "Rejected"};
        for (String status : statuses) {
            adapter.add(ClientOrderListFragment.newInstance(status.toLowerCase()), status);
        }
    }
}

class ClientOrderPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public ClientOrderPagerAdapter(FragmentManager fm) {
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