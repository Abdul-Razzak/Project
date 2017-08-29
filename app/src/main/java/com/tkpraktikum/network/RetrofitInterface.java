package com.tkpraktikum.network;

import com.tkpraktikum.model.Checkin;
import com.tkpraktikum.model.Comment;
import com.tkpraktikum.model.Response;
import com.tkpraktikum.model.User;
import com.tkpraktikum.model.Venue;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);

    @GET("places")
    Observable<List<Venue>> getVenues(@Query("query") String query, @Query("lat") Double lat, @Query("lng") Double lng);

    @GET("venue")
    Observable<Venue> getVenue(@Query("venueId") String id);

    @POST("checkinVenue")
    Observable<Response> checkin(@Query("email") String email, @Query("venueId") String venueId);

    @POST("/addComment")
    Observable<Response> addComment(@Query("email") String email, @Query("venueId") String venueId, @Query("tips") String tips);

    @GET("/getComments")
    Observable<List<Comment>> getComments(@Query("venueId") String venueId, @Query("email") String email);

    @GET("/getCheckinInfo")
    Observable<List<Checkin>> getCheckinInfo(@Query("venueId") String venueId, @Query("email") String email);

}
