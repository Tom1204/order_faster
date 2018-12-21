package com.example.iut.finalproject.manage.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.Login;
import com.example.iut.finalproject.manage.ui.fragments.OrderItemListFragment;
import com.example.iut.finalproject.models.ManagerOrder;
import com.mindorks.placeholderview.annotations.Resolve;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDescriptionActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private ManagerOrder order;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (ManagerOrder) getIntent().getSerializableExtra("order");
        setContentView(R.layout.activity_order_description);
        ButterKnife.bind(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.order_item_list_frame, OrderItemListFragment.newInstance(order.getOrderItems()))
                .commit();
    init();
    }

    private void init() {
        orderId.setText(String.valueOf(order.getId()));
        orderTime.setText(order.getCreatedDateTime().toString());
        orderUserName.setText(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        orderTableNumber.setText(String.valueOf(order.getTableNumber()));
        orderNumberOfMeals.setText(String.valueOf(order.getOrderItems().size()));
        orderTotalPrice.setText(String.valueOf(order.getTotalPrice()));
        orderStatus.setText(order.getStatus());

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


    public ManagerOrder getOrder() {
        return order;
    }

    public void setOrder(ManagerOrder order) {
        this.order = order;
    }
}