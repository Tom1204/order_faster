package com.example.iut.finalproject.rest_api;

import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Category;
import com.example.iut.finalproject.models.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RouterApi {

    @GET("main/category/")
    Call<ArrayResponse<Category>> getCategories();

    @GET("main/item/")
    Call<ArrayResponse<Item>> getItems(@Query("category") int categoryId);
}
