package com.example.gor.githubwatcher.model.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.gor.githubwatcher.R;
import com.example.gor.githubwatcher.model.holders.CommitHolder;
import com.example.gor.githubwatcher.model.Commit;

import java.util.List;


/**
 * Created by Gor on 27.09.2017.
 */

public class CommitListAdapter extends ArrayAdapter<Commit>{

    private final Activity context;
    private CommitHolder holder;
    private List<Commit> commitList;
    private Commit commit;

    public CommitListAdapter(@NonNull Activity context,
                             @NonNull List<Commit> list) {
        super(context, R.layout.commit_item, list);

        commitList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = context.getLayoutInflater().inflate(R.layout.commit_item, parent, false);
            holder = new CommitHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (CommitHolder) view.getTag();
        }

        commit = commitList.get(position);

        holder.setCommitAuthor(commit.getAuthor());
        holder.setCommitDate(commit.getDate());
        holder.setCommitHash(commit.getHash());
        holder.setCommitMessage(commit.getMessage());

        return view;
    }

    @Override
    public int getCount() {
        return commitList.size();
    }
}
