package com.example.gor.githubwatcher.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gor on 22.09.2017.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
