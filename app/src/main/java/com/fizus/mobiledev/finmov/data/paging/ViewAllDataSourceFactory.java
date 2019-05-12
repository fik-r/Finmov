package com.fizus.mobiledev.finmov.data.paging;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class ViewAllDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private final MutableLiveData<ViewAllDataSource> viewAllDataSourceLiveData;
    private final MovieRepository movieRepository;
    private final CompositeDisposable compositeDisposable;
    private Movie.Type type = null;
    private long movieId = 0;

    public ViewAllDataSourceFactory(MovieRepository movieRepository, CompositeDisposable compositeDisposable){
        this.movieRepository = movieRepository;
        this.compositeDisposable = compositeDisposable;
        viewAllDataSourceLiveData = new MutableLiveData<>();

    }

    public void setType(Movie.Type type) {
        this.type = type;
    }
    public void setMovieId(long movieId) { this.movieId = movieId; }

    @Override
    public DataSource<Integer, Movie> create() {
        ViewAllDataSource viewAllDataSource = new ViewAllDataSource(movieRepository, compositeDisposable, type, movieId);
        viewAllDataSourceLiveData.postValue(viewAllDataSource);
        return viewAllDataSource;
    }
}
