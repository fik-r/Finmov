package com.fizus.mobiledev.finmov.ui.main;

import android.util.Log;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;
import com.fizus.mobiledev.finmov.utils.SchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class MainViewModel extends ViewModel {

    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final MovieRepository movieRepository;
    private final MutableLiveData<List<Movie>> nowPlayingMoviesLiveData;
    private final MutableLiveData<List<Movie>> upcomingMoviesLiveData;

    private final static String TAG = MainViewModel.class.getSimpleName();

    @Inject
    public MainViewModel(MovieRepository movieRepository, SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.movieRepository = movieRepository;
        this.nowPlayingMoviesLiveData = new MutableLiveData<>();
        this.upcomingMoviesLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public MutableLiveData<List<Movie>> getNowPlayingMovies(int page, String region) {
        compositeDisposable.add(movieRepository.getNowPlayingMovies(page, region)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                    Log.e(TAG, "accept: " + movies.size());
                    nowPlayingMoviesLiveData.postValue(movies);
                }, throwable -> Log.e(TAG, throwable.getLocalizedMessage()))
        );
        return nowPlayingMoviesLiveData;
    }

    public MutableLiveData<List<Movie>> getUpcomingMovies(int page) {
        compositeDisposable.add(movieRepository.getUpcomingMovies(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                    Log.e(TAG, "getUpcomingMovies: " + movies.size());
                    upcomingMoviesLiveData.postValue(movies);
                }, throwable -> Log.e(TAG, "getUpcomingMovies: " + throwable.getLocalizedMessage()))
        );
        return upcomingMoviesLiveData;
    }
}
