package com.thedancercodes.githubrxjava;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheDancerCodes on 29/05/2018.
 */

public class GitHubRepoAdapter extends BaseAdapter {

    private List<GitHubRepo> mGitHubRepos = new ArrayList<>();

    @Override
    public int getCount() {
        return mGitHubRepos.size();
    }

    @Override
    public GitHubRepo getItem(int position) {
        if (position < 0 || position >= mGitHubRepos.size()) {
            return null;
        } else {
            return mGitHubRepos.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GithubRepoViewHolder viewHolder = (GithubRepoViewHolder) view.getTag();
        viewHolder.setGithubRepo(getItem(position));
        return view;
    }

    public void setGitHubRepos(@Nullable List<GitHubRepo> repos) {
        if (repos == null) {
            return;
        }
        mGitHubRepos.clear();
        mGitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        final GithubRepoViewHolder viewHolder = new GithubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class GithubRepoViewHolder {
        private TextView textRepoName;
        private TextView textRepoDescription;
        private TextView textLanguage;
        private TextView textStars;

        public GithubRepoViewHolder(View view) {
            textRepoName = view.findViewById(R.id.text_repo_name);
            textRepoDescription = view.findViewById(R.id.text_repo_description);
            textLanguage = view.findViewById(R.id.text_language);
            textStars = view.findViewById(R.id.text_stars);
        }

        public void setGithubRepo(GitHubRepo githubRepo) {
            textRepoName.setText(githubRepo.name);
            textRepoDescription.setText(githubRepo.description);
            textLanguage.setText("Language: " + githubRepo.language);
            textStars.setText("Stars: " + githubRepo.stargazersCount);
        }

    }

}
