package com.example.gor.githubwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.gor.githubwatcher.model.Commit;
import com.example.gor.githubwatcher.model.DataSourceRepos;
import com.example.gor.githubwatcher.model.RepoItem;
import com.example.gor.githubwatcher.model.adapters.RepoListAdapter;
import com.example.gor.githubwatcher.service.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListReposActivity extends AppCompatActivity {

    private String accessToken;
    List<String> repoList = new ArrayList<String>();
    DataSourceRepos data = DataSourceRepos.getInstance();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accessToken = getIntent().getStringExtra("access_token");

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

        final GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<RepoItem>> clientRepositoriesCall = client.getRepositories();
        clientRepositoriesCall.enqueue(new Callback<List<RepoItem>>() {
            @Override
            public void onResponse(Call<List<RepoItem>> call, Response<List<RepoItem>> response) {
                Toast.makeText(ListReposActivity.this, "Getting list repo success", Toast.LENGTH_SHORT).show();

                data.setRepos(response.body());
                if (response.body() != null) {
                    for (RepoItem repo : response.body()) {
                        repoList.add(repo.getName());
                    }
                } else
                    Toast.makeText(ListReposActivity.this, "Response EMPTY", Toast.LENGTH_SHORT).show();

                recyclerView = new RecyclerView(ListReposActivity.this);

                RepoListAdapter recyclerAdapter = new RepoListAdapter(getLayoutInflater(), repoList);
                recyclerAdapter.setOnItemClickCallback(new RepoListAdapter.OnItemClickCallback() {
                    @Override
                    public void onClick(final int i) {

                        final Call<List<Commit>> repoCommitsCall =
                                client.getRepoCommits(
                                        data.getSelectedRepo(i).getAuthor(),
                                        data.getSelectedRepo(i).getName());
                        repoCommitsCall.enqueue(new Callback<List<Commit>>() {
                            @Override
                            public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {

                                Toast.makeText(ListReposActivity.this,
                                        "Getting repo info success", Toast.LENGTH_SHORT).show();

                                data.getSelectedRepo(i).setCommits(response.body());

                                Intent intent = new Intent(ListReposActivity.this, ItemRepoActivity.class);
                                intent.putExtra("number_of_repo", i);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<Commit>> call, Throwable t) {
                                Toast.makeText(ListReposActivity.this,
                                        "Getting repo info fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(ListReposActivity.this, 2));
                recyclerView.setHasFixedSize(true);
                setContentView(recyclerView);

            }

            @Override
            public void onFailure(Call<List<RepoItem>> call, Throwable t) {
                Toast.makeText(ListReposActivity.this, "Getting list repo fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
