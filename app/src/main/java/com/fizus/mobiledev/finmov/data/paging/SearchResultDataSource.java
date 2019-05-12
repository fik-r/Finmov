package com.fizus.mobiledev.finmov.data.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;

public class SearchResultDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final String TAG = ViewAllDataSource.class.getSimpleName();
    private final MovieRepository movieRepository;
    private final CompositeDisposable compositeDisposable;
    private String query;

    public SearchResultDataSource(MovieRepository movieRepository, CompositeDisposable compositeDisposable, String query) {
        this.movieRepository = movieRepository;
        this.compositeDisposable = compositeDisposable;
        this.query = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        compositeDisposable.add(movieRepository.getSearchResultMovies(query, 1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                            callback.onResult(movies, null, 2);
                            Log.e(TAG, "loadInitial: " + movies.size());
                        },
                        throwable -> Log.e(TAG, "loadInitial: " + throwable.getLocalizedMessage())
                )
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        compositeDisposable.add(movieRepository.getSearchResultMovies(query, params.key)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                            int nextPage = params.key + 1;
                            callback.onResult(movies, nextPage);
                            Log.e(TAG, "loadInitial: " + movies.size());
                        },
                        throwable -> Log.e(TAG, "loadInitial: " + throwable.getLocalizedMessage())
                )
        );
    }
}
