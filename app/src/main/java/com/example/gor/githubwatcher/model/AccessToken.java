package com.example.gor.githubwatcher.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gor on 22.09.2017.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
