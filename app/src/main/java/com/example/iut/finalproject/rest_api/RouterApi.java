package com.example.iut.finalproject.rest_api;

import com.example.iut.finalproject.models.ArrayResponse;
import com.example.iut.finalproject.models.Category;
import com.example.iut.finalproject.models.Client;
import com.example.iut.finalproject.models.Item;
import com.example.iut.finalproject.models.ManagerOrder;
import com.example.iut.finalproject.models.Order;
import com.example.iut.finalproject.models.OrderItemRead;
import com.example.iut.finalproject.models.Token;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    Call<ArrayResponse<Item>> getItemsById(@Query("ids") String ids, @Query("page_size") int count);

    @POST("main/order/")
    Call<ResponseBody> requestOrder(@Header("Authorization") String auth, @Body Order order);

    @GET("main/order_item/")
    Call<ArrayResponse<OrderItemRead>> getOrderItemsByStatus(@Header("Authorization") String auth, @Query("status") String status);

    @POST("main/order_item/{order_item_id}/is_prepared/")
    Call<ResponseBody> preparedOrderItem(@Header("Authorization") String auth, @Path("order_item_id") int order_item_id);

    @PUT("main/order/{order_id}/")
    Call<ResponseBody> changeOrder(@Header("Authorization") String auth, @Path("order_id") int order_id, @Body Order order);

    @PUT("main/order/{order_id}/reject/")
    Call<ResponseBody> rejectOrder(@Header("Authorization") String auth, @Path("order_id") int order_id);

    @GET("main/order/extended/")
    Call<ArrayResponse<ManagerOrder>> getExtendedOrdersByStatus(@Header("Authorization") String auth, @Query("status") String status);

}
