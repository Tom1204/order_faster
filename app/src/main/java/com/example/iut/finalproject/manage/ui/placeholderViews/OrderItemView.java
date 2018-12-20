package com.example.iut.finalproject.manage.ui.placeholderViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.OrderItemRead;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Layout(R.layout.order_item_view)
public class OrderItemView {
    private OrderItemRead orderItem;
    private Context context;

    @View(R.id.order_item_food_title)
    TextView foodTitle;

    @View(R.id.order_item_count)
    TextView itemCount;

    @View(R.id.order_item_food_image)
    ImageView foodImage;

    private SharedPreferences sharedPreferences;

    private OnOrderItemIsDoneListener onOrderItemIsDoneListener;

    public OrderItemView(Context context, OrderItemRead orderItem, OnOrderItemIsDoneListener onOrderItemIsDoneListener) {
        sharedPreferences = context.getSharedPreferences("UserAuth", context.MODE_PRIVATE);
        this.orderItem = orderItem;
        this.context = context;
        this.onOrderItemIsDoneListener = onOrderItemIsDoneListener;
    }

    @Resolve
    public void onResolve() {
        foodTitle.setText(orderItem.getItem().getName());
        itemCount.setText(String.valueOf("Count: " + orderItem.getCount()));
        Glide.with(context).load(orderItem.getItem().getImage().getFile()).into(foodImage);
    }

    @Click(R.id.order_item_done)
    public void orderItemDone() {
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
        Call<ResponseBody> call = service.preparedOrderItem(auth, orderItem.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    onOrderItemIsDoneListener.onOrderItemIsDone(OrderItemView.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface OnOrderItemIsDoneListener {
        void onOrderItemIsDone(OrderItemView view);
    }
}
