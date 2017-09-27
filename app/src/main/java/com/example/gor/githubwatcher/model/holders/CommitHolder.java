package com.example.gor.githubwatcher.model.holders;

import android.view.View;
import android.widget.TextView;

import com.example.gor.githubwatcher.R;

/**
 * Created by Gor on 27.09.2017.
 */

public class CommitHolder {

    private TextView commitHash;
    private TextView commitAuthor;
    private TextView commitDate;
    private TextView commitMessage;

    public CommitHolder(View itemView){

        commitAuthor = (TextView) itemView.findViewById(R.id.id_commit_author);
        commitDate = (TextView) itemView.findViewById(R.id.id_commit_date);
        commitHash = (TextView) itemView.findViewById(R.id.id_commit_hash);
        commitMessage = (TextView) itemView.findViewById(R.id.id_commit_message);
    }

    public void setCommitHash(String commitHashString) {
        commitHash.setText("Hash: " + commitHashString);
    }

    public void setCommitAuthor(String commitAuthorString) {
        commitAuthor.setText("Author: " + commitAuthorString);
    }

    public void setCommitDate(String commitDateString) {
        commitDate.setText("Date: " + commitDateString);
    }

    public void setCommitMessage(String commitMessageString) {
        commitMessage.setText("Message: " + commitMessageString);
    }
}
