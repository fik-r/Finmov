package com.fizus.mobiledev.finmov.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.paging.SearchResultDataSourceFactory;
import com.fizus.mobiledev.finmov.repository.MovieRepository;
import com.fizus.mobiledev.finmov.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class MainViewModel extends ViewModel {

    private final static String TAG = MainViewModel.class.getSimpleName();
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final MovieRepository movieRepository;
    private final MutableLiveData<List<Movie>> nowPlayingMoviesLiveData;
    private final MutableLiveData<List<Movie>> upcomingMoviesLiveData;
    private final MutableLiveData<List<Movie>> popularMoviesLiveData;
    private final MutableLiveData<List<Movie>> topRatedMoviesLiveData;
    private final SearchResultDataSourceFactory searchResultDataSourceFactory;
    private LiveData<PagedList<Movie>> searchResultLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(MovieRepository movieRepository, SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.movieRepository = movieRepository;
        this.nowPlayingMoviesLiveData = new MutableLiveData<>();
        this.upcomingMoviesLiveData = new MutableLiveData<>();
        this.popularMoviesLiveData = new MutableLiveData<>();
        this.topRatedMoviesLiveData = new MutableLiveData<>();
        searchResultDataSourceFactory = new SearchResultDataSourceFactory(movieRepository, compositeDisposable);
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

    public MutableLiveData<List<Movie>> getPopularMoviesLiveData(int page) {
        compositeDisposable.add(movieRepository.getPopularMovies(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                    Log.e(TAG, "getPopularMoviesLiveData: " + movies.size());
                    popularMoviesLiveData.postValue(movies);
                }, throwable -> Log.e(TAG, "getPopularMoviesLiveData: " + throwable.getLocalizedMessage()))
        );
        return popularMoviesLiveData;
    }

    public MutableLiveData<List<Movie>> getTopRatedMoviesLiveData(int page) {
        compositeDisposable.add(movieRepository.getTopRatedMovies(page)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(movies -> {
                    Log.e(TAG, "getTopRatedMoviesLiveData: " + movies.size());
                    topRatedMoviesLiveData.postValue(movies);
                })
        );
        return topRatedMoviesLiveData;
    }

//    public LiveData<PagedList<Movie>> getSearchResultLiveData() {
//        return searchResultLiveData;
//    }

    public LiveData<PagedList<Movie>> onQueryTextChange(String query) {
        if (!query.isEmpty()) {
            PagedList.Config pagedConfig = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(1)
                    .setPageSize(2)
                    .build();

            searchResultLiveData = new LivePagedListBuilder<>(searchResultDataSourceFactory, pagedConfig)
                    .build();
            searchResultDataSourceFactory.setQuery(query);
        }
        return searchResultLiveData;
    }
}
