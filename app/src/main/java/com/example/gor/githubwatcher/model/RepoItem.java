package com.example.gor.githubwatcher.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gor on 26.09.2017.
 */

public class RepoItem {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private Author owner;

    @SerializedName("forks_count")
    private String forksCount;

    @SerializedName("watchers")
    private String watchers;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getForksCount() {
        return forksCount;
    }

    public String getWatchers() {
        return watchers;
    }

    public String getAuthor() {
        return owner.getAuthor();
    }

    public String getAvatarUrl() {
        return owner.getAvatarUrl();
    }


    public class Author{

        @SerializedName("login")
        private String author;
        @SerializedName("avatar_url")
        private String  avatarUrl;

        public String getAuthor() {
            return author;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }
    }

    //-------------------------------------------

    private List<Commit> commits;

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }
}
