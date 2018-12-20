package com.example.iut.finalproject.manage.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.manage.ui.fragments.OrderItemFragment;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderItemActivity extends AppCompatActivity {


    @BindView(R.id.orderviewpager)
    public ViewPager viewPager;

    @BindView(R.id.orderviewpagertab)
    public TabLayout viewPagerTab;

    OrderItemPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        ButterKnife.bind(this);
        adapter = new OrderItemPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPagerTab.setupWithViewPager(viewPager);
        initViewPager();
    }

    private void initViewPager() {
        String[] statuses = new String[]{"Pending", "Prepared"};
        for (String status : statuses) {
            adapter.add(OrderItemFragment.newInstance(status.toLowerCase()), status);
        }
    }
}

class OrderItemPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public OrderItemPagerAdapter(FragmentManager fm) {
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