package com.example.iut.finalproject.client.ui.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.placeholderViews.CartFoodItemView;
import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Item;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartFoodItemView.OnOrderItemDeleteListener {

    private LocalDb localDb;
    private List<Item> items;
    private List<Integer> itemIds;
    private List<OrderItem> orderItems;
    private ItemDao itemDao;
    private double totalPriceDouble = 0;


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

        initCart();
    }

    private void initCart() {
        itemDao = localDb.getItemDao();
        itemIds = itemDao.allItems();
        String ids = "";
        for (Integer itemId : itemIds) {
            ids += "-" + itemId;
            Log.d("ItemSize", "" + itemId);
        }
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);

        Call<ArrayResponse<Item>> call = service.getItemsById(ids);
        call.enqueue(new Callback<ArrayResponse<Item>>() {
            @Override
            public void onResponse(Call<ArrayResponse<Item>> call, Response<ArrayResponse<Item>> response) {
                if (response.isSuccessful()) {
                    items = response.body().list;
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
        Log.d("OrderItemsSize", "" + items.size());
        orderItems = itemDao.allOrderItem();
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
                "Total price: " + totalPriceDouble + " sum"
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
                "Your order: " + totalPriceDouble + " sum"
        ));
    }
}
