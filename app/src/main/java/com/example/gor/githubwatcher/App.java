package com.example.gor.githubwatcher;

import android.app.Application;
import android.content.Context;

import com.example.gor.githubwatcher.di.AppComponent;
import com.example.gor.githubwatcher.di.DaggerAppComponent;
import com.example.gor.githubwatcher.di.DataModule;
import com.example.gor.githubwatcher.di.WebModule;

/**
 * Created by Gor on 03.10.2017.
 */

public class App extends Application {
    AppComponent component;

    public static AppComponent component(Context context){
        return ((App)context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .webModule(new WebModule())
                .dataModule(new DataModule())
                .build();
    }
}
