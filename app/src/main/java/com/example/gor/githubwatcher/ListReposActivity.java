package com.example.gor.githubwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.gor.githubwatcher.di.AppComponent;
import com.example.gor.githubwatcher.di.DaggerAppComponent;
import com.example.gor.githubwatcher.di.WebModule;
import com.example.gor.githubwatcher.model.Commit;
import com.example.gor.githubwatcher.model.DataSourceRepos;
import com.example.gor.githubwatcher.model.RepoItem;
import com.example.gor.githubwatcher.model.adapters.RepoListAdapter;
import com.example.gor.githubwatcher.service.GitHubClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListReposActivity extends AppCompatActivity {

    private String accessToken;
    List<String> repoList = new ArrayList<String>();

    /*@Inject*/
    DataSourceRepos data;

    RecyclerView recyclerView;

    private AppComponent component2;
    /*@Inject @Named("api_client")*/
    /*GitHubClient client;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accessToken = getIntent().getStringExtra("access_token");

        component2 = DaggerAppComponent.builder()
                .webModule(new WebModule(accessToken))
                .build();
        final GitHubClient client = component2.client();
        data = App.component(this).data();

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

                        Call<List<Commit>> repoCommitsCall =
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
