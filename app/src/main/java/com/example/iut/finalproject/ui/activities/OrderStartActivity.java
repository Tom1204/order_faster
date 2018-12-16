package com.example.iut.finalproject.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.iut.finalproject.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_start);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.new_order)
    void createNewOrder(View view) {
        Intent intent = new Intent(this, FoodActivity.class);
        startActivityForResult(intent, 100);
    }
}
