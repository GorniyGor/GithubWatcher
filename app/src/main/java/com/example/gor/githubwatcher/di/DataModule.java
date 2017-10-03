package com.example.gor.githubwatcher.di;

import com.example.gor.githubwatcher.model.DataSourceRepos;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gor on 29.09.2017.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    public DataSourceRepos provideDataSourceRepos(){
        return new DataSourceRepos();
    }
}


