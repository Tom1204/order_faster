package com.example.iut.finalproject.rest_api;

import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Category;
import com.example.iut.finalproject.models.Client;
import com.example.iut.finalproject.models.Item;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.models.Token;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouterApi {

    @FormUrlEncoded
    @POST("main/login/")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

    @GET("main/category/")
    Call<ArrayResponse<Category>> getCategories();

    @GET("main/item/")
    Call<ArrayResponse<Item>> getItems(@Query("category") int categoryId);

    @POST("main/register/")
    Call<Token> registerClient(@Body Client client);

    @GET("main/item/")
    Call<ArrayResponse<Item>> getItemsById(@Query("ids") String ids);

    @POST("main/order/")
    Call<ResponseBody> requestOrder(@Header("Authorization") String auth, @Body Order order);
}
