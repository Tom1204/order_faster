package com.example.iut.finalproject.client.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.ErrorHandler;
import com.example.iut.finalproject.models.Token;
import com.example.iut.finalproject.rest_api.RestClient;
import com.example.iut.finalproject.rest_api.RouterApi;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends RequiredFields {

    @BindView(R.id.username)
    public EditText username;

    @BindView(R.id.password)
    public EditText password;

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requiredFields = new EditText[]{username, password};
        mPref = getSharedPreferences("UserAuth", MODE_PRIVATE);
        if (mPref.contains("token")) {
            Intent intent = new Intent(Login.this, OrderStartActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.login_action)
    public void login(View view) {
        if (!this.checkRequiredFields()) {
            RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
            Call<Token> call = service.login(username.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = mPref.edit();
                        editor.putString("token", response.body().getToken());
                        editor.commit();
                        Intent intent = new Intent(Login.this, OrderStartActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = null;
                        try {
                            error = ErrorHandler.getErrors(response.errorBody().string());
                            ErrorHandler.getSnackbarError(findViewById(R.id.activity_login_layout), error).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @OnClick(R.id.register_action)
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
