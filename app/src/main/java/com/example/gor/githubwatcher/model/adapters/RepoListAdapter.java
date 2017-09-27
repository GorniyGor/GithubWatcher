package com.example.gor.githubwatcher.model.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gor.githubwatcher.R;
import com.example.gor.githubwatcher.model.holders.RepoNameHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Gor on 27.09.2017.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoNameHolder>{

    private OnItemClickCallback clickCallback;
    private final WeakReference<LayoutInflater> layoutInflater;
    private List<String> repoList;

    public RepoListAdapter(LayoutInflater inflater, List<String> list) {
        layoutInflater = new WeakReference<LayoutInflater>(inflater);
        repoList = list;
    }

    @Override
    public RepoNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoNameHolder(
                layoutInflater.get().inflate(
                        R.layout.activity_repos_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RepoNameHolder holder, final int i) {
        holder.setOnItemRepoClickCallback(new RepoNameHolder.OnItemRepoClickCallback() {
            @Override
            public void onClick() {
                clickCallback.onClick(i);
            }
        });
        holder.setTextView(repoList.get(i));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public interface OnItemClickCallback{
        void onClick(int i);
    }

    public void setOnItemClickCallback(OnItemClickCallback callback){
        clickCallback = callback;
    }


}
