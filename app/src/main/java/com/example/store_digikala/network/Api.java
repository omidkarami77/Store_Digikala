package com.example.store_digikala.network;


import java.util.List;
import com.example.store_digikala.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String spacify = "consumer_key=ck_0ecdbf5630575e2ea2d86a9fe5c913b0682510aa&consumer_secret=cs_545b1f473ce462428c311e9d1cf371a0e6f90729";
    @GET("products?" + spacify)
    Call<List<Products>> getProductList();

    @GET("products?consumer_key=ck_0ecdbf5630575e2ea2d86a9fe5c913b0682510aa&consumer_secret=cs_545b1f473ce462428c311e9d1cf371a0e6f90729&orderby=rating")
    Call<List<Products>> getPopularity();

    @GET("products?consumer_key=ck_0ecdbf5630575e2ea2d86a9fe5c913b0682510aa&consumer_secret=cs_545b1f473ce462428c311e9d1cf371a0e6f90729&orderby=popularity")
    Call<List<Products>> getTBestSelerList();

    @GET("products/{id}?" + spacify)
    Call<Products> getProduct(@Path("id") Integer id);

    @GET("products/categories/?" + spacify)
    Call<List<Categories>> getCategories();

    @GET("products?" + spacify)
    Call<List<Products>> getProductsWithCatId(@Query("category") String categoryId);

    @GET("products?" + spacify)
    Call<List<Products>> getProductWithSearch(@Query("search") String searchstring);

    @GET()
    Call<List<Products>> getProductSearchByFilter(@Query("search") String searchstring, @Query("filter") String fiterstring);

    @POST("customers?" + spacify)
    @FormUrlEncoded
    Call<Customer> createCustomer(@Field("first_name") String first_name,
                                                                           @Field("last_name") String last_name,
                                                                           @Field("username") String username,
                                                                           @Field("email") String email);

    @POST("orders?" + spacify)
    Call<Customer> sendOrder(@Body OrderJsonBody body);

    @GET("coupons" + spacify)
    Call<List<Coupon>> getCoupons();

    @GET(" products/reviews?"+spacify)
    Call<List<Review>> getProductReviewsById(@Query("product") int productId);

    @POST("products/reviews?"+spacify)
    @FormUrlEncoded
    Call<Customer> sendNewReview(@Field("product_id") int product_id, @Field("review") String review, @Field("reviewer") String reviewer_name, @Field("reviewer_email") String email);

}
