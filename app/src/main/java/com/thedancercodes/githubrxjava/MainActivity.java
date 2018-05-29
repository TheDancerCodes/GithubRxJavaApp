package com.thedancercodes.githubrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter mAdapter = new GitHubRepoAdapter();
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView listView = findViewById(R.id.list_view_repos);
        listView.setAdapter(mAdapter);

        final EditText editTextUsername = findViewById(R.id.edit_text_username);
        final Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getStarredRepos(String username) {
        mSubscription = GitHubClient
                            .getInstance()
                            .getStarredRepos(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<GitHubRepo>>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(TAG, "In onCompleted()");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "In onError()");
                                }

                                @Override
                                public void onNext(List<GitHubRepo> gitHubRepos) {
                                    Log.d(TAG, "In onNext()");
                                    mAdapter.setGitHubRepos(gitHubRepos);

                                }
                            });

    }

}
