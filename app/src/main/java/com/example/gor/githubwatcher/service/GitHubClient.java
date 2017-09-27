package com.example.gor.githubwatcher.service;

import com.example.gor.githubwatcher.model.AccessToken;
import com.example.gor.githubwatcher.model.RepoItem;
import com.example.gor.githubwatcher.model.Commit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<List<RepoItem>> getRepositories();

    @GET("/repos/{owner}/{repo}/commits")
    Call<List<Commit>> getRepoCommits(@Path("owner") String owner, @Path("repo") String repoName);


}
