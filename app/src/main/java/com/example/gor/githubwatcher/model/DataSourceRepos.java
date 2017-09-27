package com.example.gor.githubwatcher.model;

import java.util.List;

/**
 * Created by Gor on 26.09.2017.
 */

public class DataSourceRepos {
    private static final DataSourceRepos ourInstance = new DataSourceRepos();

    public static DataSourceRepos getInstance() {
        return ourInstance;
    }
    //--------------------------------------------------------------------

    private List<RepoItem> repos;


    public RepoItem getSelectedRepo(int i) {
        return repos.get(i);
    }

    public void setRepos(List<RepoItem> repos) {
        this.repos = repos;
    }


}
