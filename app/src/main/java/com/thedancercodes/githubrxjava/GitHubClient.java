package com.thedancercodes.githubrxjava;


import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * GitHubClient:
 * The object we will interact with to make network calls from the UI level.
 */
public class GitHubClient {

    private static final String GITHOB_BASE_URL = "https://api.github.com/";

    private static GitHubClient instance;
    private GitHubService mGitHubService;

    private GitHubClient() {
        final Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        // Pass in an RxJavaCallAdapterFactory as the call adapter so that network calls can
        // return Observable objects

        // Pass in a GsonConverterFactory so that we can use Gson as a way to
        // marshal JSON objects to Java objects.
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHOB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mGitHubService = retrofit.create(GitHubService.class);
    }

    public static GitHubClient getInstance() {
        if (instance == null) {
            instance = new GitHubClient();
        }
        return instance;
    }

    public Observable<List<GitHubRepo>> getStarredRepos(@NonNull String userName) {
        return mGitHubService.getStarredRepositories(userName);
    }
}
