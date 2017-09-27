package com.example.gor.githubwatcher.model.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gor.githubwatcher.R;

/**
 * Created by Gor on 27.09.2017.
 */

public class RepoNameHolder extends RecyclerView.ViewHolder {

    private OnItemRepoClickCallback repoClickCallback;

    private TextView textView;
    private int position;

    public RepoNameHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.id_repo_item);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repoClickCallback.onClick();
            }
        });
    }

    public void setTextView(String text) {
        textView.setText(text);
    }

    //------------------------------------------------------------------

    public interface OnItemRepoClickCallback{
        void onClick();
    }

    public void setOnItemRepoClickCallback(OnItemRepoClickCallback callback){
        repoClickCallback = callback;
    }


}
