package com.example.iut.finalproject.client.ui.placeholderViews;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.Database.LocalDb;
import com.example.iut.finalproject.Database.Model.ItemDao;
import com.example.iut.finalproject.Database.Model.OrderItem;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.ItemDetailActivity;
import com.example.iut.finalproject.models.Item;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.food_item_view)
public class FoodItemView {
    private Item item;
    private Context context;

    private LocalDb localDb;

    public FoodItemView(Item item, Context context) {
        localDb = Room.
                databaseBuilder(context, LocalDb.class, "local_db").
                fallbackToDestructiveMigration().
                allowMainThreadQueries().
                build();
        this.item = item;
        this.context = context;
    }

    @View(R.id.food_title)
    public TextView foodTitle;

    @View(R.id.food_description)
    public TextView description;

    @View(R.id.food_count)
    public TextView foodCount;

    @View(R.id.food_price)
    TextView foodPrice;

    @View(R.id.food_image)
    ImageView foodImage;

    @Click(R.id.card_view)
    void detailShow() {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }

    @Resolve
    public void onResolve() {
        foodTitle.setText(item.getName());
        description.setText(item.getDescription());
        String price = String.valueOf(item.getPrice()) + " $";
        foodPrice.setText(price);
        Glide.with(context).load(item.getImage().getFile()).into(foodImage);
    }

    @Click(R.id.food_adding)
    public void addingFoodAction() {
        int count = Integer.parseInt(foodCount.getText().toString());
        count++;
        foodCount.setText(String.valueOf(count));
    }

    @Click(R.id.food_removing)
    public void removingFoodAction() {
        int count = Integer.parseInt(foodCount.getText().toString());
        if (count != 0)
            count--;
        foodCount.setText(String.valueOf(count));
    }

    @Click(R.id.food_add_card)
    public void addToCard() {
        int count = Integer.parseInt(foodCount.getText().toString());
        if (count == 0)
            return;
        ItemDao itemDao = localDb.getItemDao();
        itemDao.insert(new OrderItem(item.getId(), count));
        foodCount.setText(String.valueOf(0));
    }
}
