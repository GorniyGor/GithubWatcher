package com.example.gor.githubwatcher.di;

import android.support.v7.app.AppCompatActivity;

import com.example.gor.githubwatcher.model.DataSourceRepos;
import com.example.gor.githubwatcher.service.GitHubClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Gor on 28.09.2017.
 */

@Singleton
@Component(modules = {WebModule.class, DataModule.class})
public interface AppComponent {
    void inject(AppCompatActivity activity);
    GitHubClient client();
    DataSourceRepos data();
}
