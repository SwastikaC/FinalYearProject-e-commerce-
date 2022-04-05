package com.example.lookclassy.api;

import com.example.lookclassy.api.response.AddressResponse;
import com.example.lookclassy.api.response.AllProductResponse;
import com.example.lookclassy.api.response.CategoryResponse;
import com.example.lookclassy.api.response.DashResponse;
import com.example.lookclassy.api.response.LoginResponse;
import com.example.lookclassy.api.response.OrderHistoryResponse;
import com.example.lookclassy.api.response.RegisterResponse;
import com.example.lookclassy.api.response.SingleProductResponse;
import com.example.lookclassy.api.response.SliderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {


    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/cart")
    Call<AllProductResponse> addToCart(@Header("api_key") String apikey, @Field("p_id") int p, @Field("quantity") int q);

    @FormUrlEncoded
    @POST("/api/v1/order")
    Call<RegisterResponse> order(@Header("api_key") String apikey,
                                 @Field("p_type") int p_type,
                                 @Field("address_id") int address_id,
                                 @Field("payment_refrence") String paymentRefrence);

    @GET("/api/v1/order")
    Call<OrderHistoryResponse> orderHistory(@Header("api_key") String apikey
    );


    @GET("/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();

    @GET("/api/v1/get-categories")
    Call<CategoryResponse> getCategories();

    @GET("/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int catID);

    @GET("/api/v1/cart")
    Call<AllProductResponse> getMyCart(@Header("api_key") String apikey);

    @DELETE("/api/v1/cart")
    Call<RegisterResponse> deleteFromCart(@Header("api_key") String apikey, @Query("c_id") int cartID);

    @GET("/api/v1/address")
    Call<AddressResponse> getMyAddresses(@Header("api_key") String apikey);


    @GET("/api/v1/dash")
    Call<DashResponse> getDash(@Header("api_key") String apikey);

    @DELETE("/api/v1/category")
    Call<RegisterResponse> deleteCategory(@Header("api_key") String apikey, @Query("c_id") int id);

    @GET("/api/v1/wishlist")
    Call<AllProductResponse> wishlist(@Header("api_key") String apikey);
    @Multipart
    @POST("/api/v1/upload-product")
    Call<RegisterResponse> uploadProduct(
            @Header("api_key") String apikey,
            @Part MultipartBody.Part[] files,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("quantity") RequestBody quantity,
            @Part("discount_price") RequestBody discount_price,
            @Part("categories") RequestBody categories
    );

    @Multipart
    @POST("/api/v1/upload-category")
    Call<RegisterResponse> uploadCategory(
            @Header("api_key") String apikey,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name

    );
    @GET("/api/v1/get-all-products")
    Call<SingleProductResponse> getProductById(@Query("id") int c_id);

    @FormUrlEncoded
    @POST("/api/v1/wishlist")
    Call<AllProductResponse> addtowishlist(@Header("api_key") String apikey, @Field("p_id") int p);

    @GET("/api/v1/wishlist")
    Call<AllProductResponse> getMyWishlist(@Header("api_key") String apikey);

    @DELETE("/api/v1/wishlist")
    Call<RegisterResponse> deleteFromWishlist(@Header("api_key") String apikey, @Query("w_id") int wishlistID);

}
