package com.example.iut.finalproject.client.ui.activities;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.placeholderViews.CartFoodItemView;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.ErrorHandler;
import com.example.iut.finalproject.models.Item;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartFoodItemView.OnOrderItemDeleteListener {

    private LocalDb localDb;
    private List<Item> items;
    private ItemDao itemDao;
    private double totalPriceDouble = 0;
    private SharedPreferences mPref;


    @BindView(R.id.cart_placeholder_view)
    public InfinitePlaceHolderView placeHolderView;

    @BindView(R.id.cart_total_price)
    public TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        localDb = Room.
                databaseBuilder(this, LocalDb.class, "local_db").
                fallbackToDestructiveMigration().
                allowMainThreadQueries().
                build();
        mPref = getSharedPreferences("UserAuth", MODE_PRIVATE);

        initCart();
    }

    private void initCart() {
        itemDao = localDb.getItemDao();
        List<Integer> itemIds = itemDao.allItems();
        String ids = "";
        for (Integer itemId : itemIds) {
            ids += "-" + itemId;
            Log.d("ItemSize", "" + itemId);
        }
        if (itemIds.size() > 0)
            ids = ids.substring(1);
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);

        Call<ArrayResponse<Item>> call = service.getItemsById(ids, itemIds.size());
        call.enqueue(new Callback<ArrayResponse<Item>>() {
            @Override
            public void onResponse(Call<ArrayResponse<Item>> call, Response<ArrayResponse<Item>> response) {
                if (response.isSuccessful()) {
                    items = response.body().list;
                    if (items.size() > 0)
                        addViews(items);
                }
            }

            @Override
            public void onFailure(Call<ArrayResponse<Item>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void addViews(List<Item> items) {
        if (items.size() == 0)
            return;
        List<OrderItem> orderItems = itemDao.allOrderItem();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            for (int j = 0; j < items.size(); j++) {
                Item item = items.get(j);
                if (item.getId() == orderItem.getItem()) {
                    CartFoodItemView view = new CartFoodItemView(this, item, orderItem, this);
                    placeHolderView.addView(view);
                    totalPriceDouble += view.totalPrice;
                }
            }
        }
        totalPrice.setText(String.valueOf(
                "Total price: " + totalPriceDouble + " $"
        ));
    }

    @Override
    public void onOrderItemDeleted(CartFoodItemView view) {
        placeHolderView.removeView(view);
        totalPriceDouble = 0;
        for (int i = 0; i < placeHolderView.getAllViewResolvers().size(); i++) {
            CartFoodItemView orderItemView = (CartFoodItemView) placeHolderView.getAllViewResolvers().get(i);
            totalPriceDouble += orderItemView.totalPrice;
        }
        totalPrice.setText(String.valueOf(
                "Total price: " + totalPriceDouble + " $"
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete_order) {
            if (totalPriceDouble > 0.0)
                askTableInput();
        }
        return super.onOptionsItemSelected(item);
    }

    private void askTableInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm order to table");
        final EditText tableNumber = new EditText(this);
        tableNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(tableNumber);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            int table = Integer.parseInt(tableNumber.getText().toString());
            Order order = new Order(table, itemDao.allOrderItem());

            RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
            Call<ResponseBody> response = service.requestOrder(
                    "Token " + mPref.getString("token", ""), order);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        itemDao.clear();
                        finish();
                    } else {
                        String errors;
                        if (response.code() == 403)
                            errors = "Authorization credentials is not provided";
                        else
                            errors = ErrorHandler.getErrors(response.body().toString());
                        ErrorHandler.getSnackbarError(findViewById(R.id.cart_layout), errors).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
