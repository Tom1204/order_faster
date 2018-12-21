package com.example.iut.finalproject.manage.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.Login;
import com.example.iut.finalproject.models.Order;
import com.mindorks.placeholderview.annotations.View;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDescriptionActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private Order order;

    @BindView(R.id.order_item_list_frame)
    public FrameLayout frameLayout;

    @BindView(R.id.order_id)
    TextView orderId;

    @BindView(R.id.order_time)
    TextView orderTime;

    @BindView(R.id.order_user_name)
    TextView orderUserName;

    @BindView(R.id.order_table_number)
    TextView orderTableNumber;

    @BindView(R.id.order_number_of_meals)
    TextView orderNumberOfMeals;

    @BindView(R.id.order_total_price)
    TextView orderTotalPrice;

    @BindView(R.id.order_status)
    TextView orderStatus;

    @BindView(R.id.order_comment)
    TextView orderComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_description);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout_action) {
            sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}