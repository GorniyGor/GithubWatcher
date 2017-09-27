package com.example.gor.githubwatcher.model.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gor on 27.09.2017.
 */

public class RepoListHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public RepoListHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.id_repo_item);
    }

    public void setTextView(String text) {
        textView.setText(text);

    }
}
