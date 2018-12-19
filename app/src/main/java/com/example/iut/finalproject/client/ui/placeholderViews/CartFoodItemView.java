package com.example.iut.finalproject.client.ui.placeholderViews;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.Item;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.cart_food_item)
public class CartFoodItemView {

    private Context context;
    private Item item;
    private OrderItem orderItem;
    public double totalPrice;

    @View(R.id.cart_food_title)
    TextView foodTitle;

    @View(R.id.cart_food_description)
    TextView foodDescription;

    @View(R.id.cart_food_image)
    ImageView foodImage;

    @View(R.id.cart_food_price)
    TextView foodPrice;

    private LocalDb localDb;
    private OnOrderItemDeleteListener listener;

    @Resolve
    public void onResolve() {
        foodTitle.setText(item.getName());
        foodDescription.setText(item.getDescription());
        String builder = String.valueOf(item.getPrice()) +
                " x " +
                orderItem.getCount() +
                " = " + totalPrice;
        foodPrice.setText(builder);

        Glide.with(context).load(item.getImage().getFile()).into(foodImage);
    }

    public CartFoodItemView(Context context, Item item, OrderItem orderItem, OnOrderItemDeleteListener listener) {
        this.context = context;
        this.item = item;
        this.orderItem = orderItem;

        localDb = Room.
                databaseBuilder(context, LocalDb.class, "local_db").
                fallbackToDestructiveMigration().
                allowMainThreadQueries().
                build();
        totalPrice = item.getPrice() * orderItem.getCount();

        this.listener = listener;
    }

    @Click(R.id.cart_food_remove)
    public void removeFood() {
        localDb.getItemDao().delete(orderItem);
        listener.onOrderItemDeleted(this);
    }

    public interface OnOrderItemDeleteListener {
        public void onOrderItemDeleted(CartFoodItemView view);
    }
}
