package com.example.iut.finalproject.manage.ui.placeholderViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.models.OrderItemRead;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Layout(R.layout.order_item_view)
public class OrderItemView {
    private OrderItemRead orderItem;
    private Context context;

    @View(R.id.order_item_food_image)
    ImageView orderItemFoodImage;

    @View(R.id.order_item_title)
    TextView orderItemTitle;

    @View(R.id.order_item_description)
    TextView orderItemDescription;

    @View(R.id.order_item_total_price)
    TextView orderItemTotalPrice;

    @Toggle(R.id.order_item_ready_toogle)
    ToggleButton orderItemReadyToogle;

    private SharedPreferences sharedPreferences;

    private OnOrderIsDoneListener onOrderIsDoneListener;

    public OrderItemView(Context context, OrderItemRead orderItem, OnOrderIsDoneListener onOrderIsDoneListener) {
        sharedPreferences = context.getSharedPreferences("UserAuth", context.MODE_PRIVATE);
        this.orderItem = orderItem;
        this.context = context;
        this.onOrderIsDoneListener = onOrderIsDoneListener;
    }

    @Resolve
    public void onResolve() {
        Glide.with(context).load(orderItem.getItem().getImage().getFile()).into(orderItemFoodImage);
        orderItemTitle.setText(orderItem.getItem().getName());
        orderItemDescription.setText(orderItem.getItem().getDescription());
        orderItemTotalPrice.setText(String.valueOf(orderItem.getTotalPrice()));
    }

    @OnCheckedChanged(R.id.order_item_ready_toogle)
    public void onOrderItemReadyToogle() {
        System.out.println(orderItemReadyToogle.getTextOff());
        System.out.println(orderItemReadyToogle.getTextOn());
    }

    public interface OnOrderIsDoneListener {
        void onOrderItemIsDone(OrderItemView view);
    }
}
