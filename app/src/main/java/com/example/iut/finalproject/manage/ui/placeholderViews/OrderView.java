package com.example.iut.finalproject.manage.ui.placeholderViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageButton;
import android.widget.TextView;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.manage.ui.activities.OrderDescriptionActivity;
import com.example.iut.finalproject.models.ManagerOrder;
import com.example.iut.finalproject.models.Order;
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

@Layout(R.layout.order_view)
public class OrderView {
    private ManagerOrder order;
    private Context context;

    @View(R.id.order_id)
    TextView orderId;

    @View(R.id.order_time)
    TextView orderTime;

    @View(R.id.order_user_name)
    TextView orderUserName;

    @View(R.id.order_table_number)
    TextView orderTableNumber;

    @View(R.id.order_total_price)
    TextView orderTotalPrice;

    @View(R.id.order_cancel)
    AppCompatImageButton orderCancelButton;

    @View(R.id.order_prepare)
    AppCompatImageButton orderPrepareButton;

    private SharedPreferences sharedPreferences;

    private OnOrderRemoved onOrderRemoved;

    private OnStatusButtonHideListener onStatusButtonHideListener;

    public OrderView(Context context, ManagerOrder order, OnOrderRemoved onOrderRemoved, OnStatusButtonHideListener onStatusButtonHideListener) {
        sharedPreferences = context.getSharedPreferences("UserAuth", context.MODE_PRIVATE);
        this.order = order;
        this.context = context;
        this.onOrderRemoved = onOrderRemoved;
        this.onStatusButtonHideListener = onStatusButtonHideListener;
    }

    @Resolve
    public void onResolve() {
        orderId.setText(String.valueOf(order.getId()));
        orderTime.setText(order.getCreatedDateTime().toString());
        orderUserName.setText(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        orderTableNumber.setText(String.valueOf(order.getTableNumber()));
        orderTotalPrice.setText(String.valueOf(order.getTotalPrice()));
        onStatusButtonHideListener.onStatusButtonHide(orderCancelButton, orderPrepareButton);
    }

    @Click(R.id.order_cancel)
    public void onCancelButtonClick() {
        order.setStatus(Order.REJECTED);
        RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
        String auth = String.valueOf("Token " + sharedPreferences.getString("token", ""));
        Call<ResponseBody> call = service.rejectOrder(auth, order.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    onOrderRemoved.onOrderRemoved(OrderView.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Click(R.id.order_prepare)
    public void onPrepareButtonClick() {
        prepare();
    }

    @Click(R.id.card_view)
    public void onCardViewClick() {
        prepare();
    }

    private void prepare() {
        Intent intent = new Intent(context, OrderDescriptionActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);

    }
    public interface OnOrderRemoved{
        void onOrderRemoved(OrderView view);
    }

    public interface OnStatusButtonHideListener {
        void onStatusButtonHide(AppCompatImageButton cancel, AppCompatImageButton prepare);
    }
}
