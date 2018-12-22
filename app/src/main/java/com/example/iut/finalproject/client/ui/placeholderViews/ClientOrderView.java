package com.example.iut.finalproject.client.ui.placeholderViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageButton;
import android.widget.TextView;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.client.ui.activities.ClientOrderDescriptionActivity;
import com.example.iut.finalproject.models.ManagerOrder;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.client_order_view)
public class ClientOrderView {
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

    public ClientOrderView(Context context, ManagerOrder order) {
        sharedPreferences = context.getSharedPreferences("UserAuth", context.MODE_PRIVATE);
        this.order = order;
        this.context = context;
    }

    @Resolve
    public void onResolve() {
        orderId.setText(String.valueOf(order.getId()));
        orderTime.setText(order.getCreatedDateTime().toString());
        orderUserName.setText(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        orderTableNumber.setText(String.valueOf(order.getTableNumber()));
        orderTotalPrice.setText(String.valueOf(order.getTotalPrice() + " $"));
    }

    @Click(R.id.card_view)
    public void onCardViewClick() {
        prepare();
    }

    private void prepare() {
        Intent intent = new Intent(context, ClientOrderDescriptionActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }

}
