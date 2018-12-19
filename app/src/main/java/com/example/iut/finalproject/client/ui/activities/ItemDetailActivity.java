package com.example.iut.finalproject.client.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.food_detail_title)
    TextView foodTitle;

    @BindView(R.id.food_detail_price)
    TextView foodPrice;

    @BindView(R.id.food_detail_ingredients)
    TextView foodIngredients;

    @BindView(R.id.food_detail_description)
    TextView foodDescription;

    @BindView(R.id.food_detail_image)
    ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        Item item = (Item) getIntent().getExtras().getSerializable("item");
        foodTitle.setText(item.getName());
        foodPrice.setText("" + item.getPrice() + " sum");
        foodIngredients.setText(item.getIngredients());
        foodDescription.setText(item.getDescription());
        Glide.with(this).load(item.getImage().getFile()).into(foodImage);
    }
}
