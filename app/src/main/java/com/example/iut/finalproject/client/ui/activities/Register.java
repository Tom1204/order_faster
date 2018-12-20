package com.example.iut.finalproject.client.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.iut.finalproject.R;
import com.example.iut.finalproject.models.Client;
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

public class Register extends RequiredFields {

    @BindView(R.id.register_first_name)
    EditText firstName;

    @BindView(R.id.register_last_name)
    EditText lastName;

    @BindView(R.id.register_username)
    EditText username;

    @BindView(R.id.register_email)
    EditText email;

    @BindView(R.id.register_password)
    EditText password;

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPref = getSharedPreferences("UserAuth", MODE_PRIVATE);
        this.requiredFields = new EditText[]{firstName, lastName, username, password, email};
    }

    @OnClick(R.id.user_register_action)
    void registerUser(View view) {
        if (!this.checkRequiredFields()) {
            Client client = new Client(
                    username.getText().toString(),
                    password.getText().toString(),
                    email.getText().toString(),
                    firstName.getText().toString(),
                    lastName.getText().toString()
            );

            RouterApi service = RestClient.getRetrofitInstance().create(RouterApi.class);
            Call<Token> call = service.registerClient(client);
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = mPref.edit();
                        editor.putString("token", response.body().getToken());
                        editor.putString("token", response.body().getToken());
                        editor.putString("username", response.body().getUsername());
                        editor.putString("firstName", response.body().getFirstName());
                        editor.putString("lastName", response.body().getLastName());
                        editor.putString("userType", response.body().getUserType());
                        editor.commit();
                        Intent intent = new Intent(Register.this, FoodActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            String error;
                            if (response.code() == 400)
                                error = ErrorHandler.getErrors(response.errorBody().string());
                            else
                                error = "Server is not working, please try again later";
                            ErrorHandler.getSnackbarError(findViewById(R.id.activity_register_layout), error).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }
}
