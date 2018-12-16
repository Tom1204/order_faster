package com.example.iut.finalproject.ui.placeholderViews;


import android.widget.TextView;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.Item;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.food_item_view)
public class FoodItemView {
    private Item item;

    public FoodItemView(Item item) {
        this.item = item;
    }

    @View(R.id.food_title)
    public TextView foodTitle;

    @Resolve
    public void onResolve() {
        foodTitle.setText(item.getName());
    }
}
