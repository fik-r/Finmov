package com.fizus.mobiledev.finmov.data.paging;

import android.util.Log;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class ViewAllDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final String TAG = ViewAllDataSource.class.getSimpleName();
    private final MovieRepository movieRepository;
    private final CompositeDisposable compositeDisposable;
    private final Movie.Type type;
    private long movieId = 0;

    public ViewAllDataSource(MovieRepository movieRepository, CompositeDisposable compositeDisposable, Movie.Type type, long movieId) {
        this.movieRepository = movieRepository;
        this.compositeDisposable = compositeDisposable;
        this.type = type;
        this.movieId = movieId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        Observable<List<Movie>> moviesObservable = getMoviesByType(type, movieId,1);
        compositeDisposable.add(Objects.requireNonNull(moviesObservable)
                .subscribe(
                        movies -> {
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
        Observable<List<Movie>> moviesObservable = getMoviesByType(type, movieId, params.key);
        compositeDisposable.add(Objects.requireNonNull(moviesObservable)
                .subscribe(movies -> {
                    int nextPage = params.key + 1;
                    callback.onResult(movies, nextPage);
                }, throwable -> Log.e(TAG, "loadAfter: " + throwable.getLocalizedMessage()))
        );
    }

    private Observable<List<Movie>> getMoviesByType(Movie.Type type, long movieId, int page) {
        switch (type) {
            case UPCOMING:
                return movieRepository.getUpcomingMovies(page)
                        .debounce(400, TimeUnit.MILLISECONDS);
            case POPULAR:
                return movieRepository.getPopularMovies(page)
                        .debounce(400, TimeUnit.MILLISECONDS);
            case TOP_RATED:
                return movieRepository.getTopRatedMovies(page)
                        .debounce(400, TimeUnit.MILLISECONDS);
            case SIMILAR:
                return movieRepository.getSimilarMovies(movieId, page)
                        .debounce(400, TimeUnit.MILLISECONDS);
            case RECOMMENDATION:
                return movieRepository.getRecommendationMovies(movieId, page)
                        .debounce(400, TimeUnit.MILLISECONDS);
            default:
                return null;
        }
    }

}
