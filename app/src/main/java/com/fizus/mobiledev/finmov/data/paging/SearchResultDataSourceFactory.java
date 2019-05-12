package com.fizus.mobiledev.finmov.data.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import io.reactivex.disposables.CompositeDisposable;

public class SearchResultDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private final MutableLiveData<SearchResultDataSource> searchResultDataSourceMutableLiveData;
    private final MovieRepository movieRepository;
    private final CompositeDisposable compositeDisposable;
    private String query;

    public SearchResultDataSourceFactory(MovieRepository movieRepository, CompositeDisposable compositeDisposable){
        this.movieRepository = movieRepository;
        this.compositeDisposable = compositeDisposable;
        searchResultDataSourceMutableLiveData = new MutableLiveData<>();
    }

    public void setQuery(String query){
        this.query = query;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        SearchResultDataSource searchResultDataSource = new SearchResultDataSource(movieRepository, compositeDisposable, query);
        searchResultDataSourceMutableLiveData.postValue(searchResultDataSource);
        return searchResultDataSource;
    }
}
