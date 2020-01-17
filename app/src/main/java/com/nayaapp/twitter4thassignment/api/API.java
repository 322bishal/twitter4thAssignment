package com.nayaapp.twitter4thassignment.api;



import com.nayaapp.twitter4thassignment.model.Check;
import com.nayaapp.twitter4thassignment.model.ImageModel;
import com.nayaapp.twitter4thassignment.model.SignUpResponse;
import com.nayaapp.twitter4thassignment.model.Tweet;
import com.nayaapp.twitter4thassignment.model.User;
import com.nayaapp.twitter4thassignment.model.UsersInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("email") String username, @Field("password") String password);


    @POST("users/signup")
    Call<SignUpResponse> register(@Body User cud);

    @Multipart
    @POST("upload")
    Call<ImageModel> uploadImage(@Part MultipartBody.Part imageFile);

    @POST("users/check")
    Call<Check> check(@Body User email);

    @POST("users/showalltweet")
    Call<List<Tweet>> GetTweet(@Header("Authorization") String token);

    @POST("users/me")
    Call<UsersInfo> getUser(@Header("Authorization") String token);
}
