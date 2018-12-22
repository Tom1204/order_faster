package com.example.iut.finalproject.client.ui.placeholderViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.ManagerOrderItem;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.client_order_item_view)
public class ClientOrderItemView {
    private ManagerOrderItem orderItem;
    private Context context;

    @View(R.id.order_item_food_image)
    ImageView orderItemFoodImage;

    @View(R.id.order_item_title)
    TextView orderItemTitle;

    @View(R.id.order_item_description)
    TextView orderItemDescription;

    @View(R.id.order_item_total_price)
    TextView orderItemTotalPrice;

    @View(R.id.order_item_ready_switch)
    Switch orderItemReadySwitch;

    private SharedPreferences sharedPreferences;

    public ClientOrderItemView(Context context, ManagerOrderItem orderItem) {
        sharedPreferences = context.getSharedPreferences("UserAuth", context.MODE_PRIVATE);
        this.orderItem = orderItem;
        this.context = context;
    }

    @Resolve
    public void onResolve() {
        Glide.with(context).load(orderItem.getItem().getImage().getFile()).into(orderItemFoodImage);
        orderItemTitle.setText(orderItem.getItem().getName());
        orderItemDescription.setText(orderItem.getItem().getDescription());
        orderItemTotalPrice.setText(String.valueOf(orderItem.getTotalPrice() + " $"));
    }

    @Click(R.id.card_view_order_item)
    public void onOrderItemCardViewClick() {
        //TODO: show Food Item description
    }
}
