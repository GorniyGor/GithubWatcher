package com.example.gor.githubwatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gor.githubwatcher.model.DataSourceRepos;
import com.example.gor.githubwatcher.model.RepoItem;
import com.example.gor.githubwatcher.model.adapters.CommitListAdapter;
import com.squareup.picasso.Picasso;

public class ItemRepoActivity extends AppCompatActivity {

    ImageView avatarImage;
    TextView repoName;
    TextView author;
    TextView forksCount;
    TextView watchers;
    TextView description;

    ListView commitListView;

    RepoItem repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        int i = getIntent().getIntExtra("number_of_repo", 0);
        repo = DataSourceRepos.getInstance().getSelectedRepo(i);

        avatarImage = (ImageView) findViewById(R.id.id_avatar_image);
        repoName = (TextView) findViewById(R.id.id_repo_name);
        author = (TextView) findViewById(R.id.id_author);
        forksCount = (TextView) findViewById(R.id.id_forks_count);
        watchers = (TextView) findViewById(R.id.id_watchers);
        description = (TextView) findViewById(R.id.id_description);

        commitListView = (ListView) findViewById(R.id.id_commits_list);

        repoName.setText("Name: " + repo.getName());
        forksCount.setText("Count of fork: " + repo.getForksCount());
        watchers.setText("Watchers: " + repo.getWatchers());
        if(repo.getDescription() != null) {
            description.setText("Description: " + repo.getDescription());
        }
        else description.setText("Description: (missing)" );
        author.setText("Author: " + repo.getAuthor());
        Picasso.with(this).load(repo.getAvatarUrl()).into(avatarImage);

        if(repo.getCommits() != null) {
            CommitListAdapter adapter = new CommitListAdapter(this, repo.getCommits());
            commitListView.setAdapter(adapter);
        }
    }
}
