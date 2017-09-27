package com.example.gor.githubwatcher.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gor on 26.09.2017.
 */

public class Commit {

    @SerializedName("sha")
    private String hash;

    @SerializedName("commit")
    private CommitJson commitJson;

    public String getHash() {
        return hash;
    }

    public String getAuthor() {
        return commitJson.getCommitter().getName();
    }

    public String getDate() {
        return commitJson.getCommitter().getDate();
    }

    public String getMessage() {
        return commitJson.getMessage();
    }

    private class CommitJson {

        @SerializedName("message")
        private String message;

        @SerializedName("committer")
        private Committer committer;

        public String getMessage() {
            return message;
        }

        public Committer getCommitter() {
            return committer;
        }

        private class Committer {

            @SerializedName("name")
            private String name;

            @SerializedName("date")
            private String date;

            public String getName() {
                return name;
            }

            public String getDate() {
                return date;
            }
        }
    }
}
