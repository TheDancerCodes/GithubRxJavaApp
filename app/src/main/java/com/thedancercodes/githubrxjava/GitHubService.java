package com.thedancercodes.githubrxjava;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * GitHubService interface:
 * We will pass this interface into Retrofit and it will create an implementation of GitHubService.
 */
public interface GitHubService {

    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

}
