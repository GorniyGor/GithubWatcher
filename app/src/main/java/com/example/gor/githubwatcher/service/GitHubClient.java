package com.example.gor.githubwatcher.service;

import com.example.gor.githubwatcher.module.AccessToken;
import com.example.gor.githubwatcher.module.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Gor on 22.09.2017.
 */

public interface GitHubClient {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clentId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );

    @GET("/user/repos")
    Call<List<Repository>> getRepositories(@Query("access_token") String accessToken);
}
