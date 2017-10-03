package com.example.gor.githubwatcher.di;

import com.example.gor.githubwatcher.service.GitHubClient;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gor on 28.09.2017.
 */


@Module
public class WebModule {

    private String accessToken;

    public WebModule(){
    }

    public WebModule(String accessToken){
        this.accessToken = accessToken;
    }

    /*@Named("auth_client")*/
    @Provides
    public GitHubClient provideAuthClient(){

        if(accessToken == null) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://github.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            return retrofit.create(GitHubClient.class);
        }
        else {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request origReq = chain.request();
                            HttpUrl oldUrl = origReq.url();

                            HttpUrl newUrl = oldUrl.newBuilder().
                                    addQueryParameter("access_token", accessToken).build();

                            Request.Builder requestBuilder = origReq.newBuilder().url(newUrl);

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    }).build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            return retrofit.create(GitHubClient.class);
        }
    }

    /*@Provides @Named("api_client")
    public GitHubClient provideApiClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request origReq = chain.request();
                        HttpUrl oldUrl = origReq.url();

                        HttpUrl newUrl = oldUrl.newBuilder().
                                addQueryParameter("access_token", accessToken).build();

                        Request.Builder requestBuilder = origReq.newBuilder().url(newUrl);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit.create(GitHubClient.class);
    }*/

}
