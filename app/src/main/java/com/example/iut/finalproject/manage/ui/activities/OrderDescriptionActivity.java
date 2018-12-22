package com.example.iut.finalproject.manage.ui.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.Login;
import com.example.iut.finalproject.manage.ui.fragments.OrderItemListFragment;
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderItemView;
import com.example.iut.finalproject.manage.ui.placeholderViews.OrderView;
import com.example.iut.finalproject.models.ManagerOrder;
import com.example.iut.finalproject.models.ManagerOrderItem;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.annotations.Resolve;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDescriptionActivity extends AppCompatActivity implements OrderItemView.OnOrderItemStatusChanged {
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
    private OnOrderFinishedListener onOrderFinishedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (ManagerOrder) getIntent().getSerializableExtra("order");
        sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
        setContentView(R.layout.activity_order_description);
        ButterKnife.bind(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.order_item_list_frame, OrderItemListFragment.newInstance(order.getOrderItems(), this))
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

    @Override
    public void onOrderItemStatusChanged(ManagerOrderItem orderItem, boolean ready) {
        if (ready)
            orderItem.setStatus(ManagerOrderItem.PREPARED);
        else orderItem.setStatus(ManagerOrderItem.PENDING);
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
        Call<ResponseBody> call = service.changeOrderItemStatus(auth, orderItem.getId(), orderItem.getStatus());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.v("order_item", "OrderItem status changed successfully");
                }
                else Log.v("order_item", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        if (order.getOrderItems().ready()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDescriptionActivity.this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    order.setStatus(Order.FINISHED);
                    RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
                    String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
                    Call<ResponseBody> call = service.finishOrder(auth, order.getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.v("order_finish", "Order finish request is sent and successful");
                                onOrderFinishedListener.onOrderFinish();
                            }
                            else Log.v("order_finish", response.message());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Do you want to finish the order?")
                    .setTitle("Order preparation is finished");

            // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            order.setStatus(Order.PENDING);
        }
        Log.v("FUCKER", "finally");
        Log.v("", "");
    }

    public void setOnOrderFinishedListener(OnOrderFinishedListener onOrderFinishedListener) {
        this.onOrderFinishedListener = onOrderFinishedListener;
    }

    public interface OnOrderFinishedListener {
        void onOrderFinish();
    }
}