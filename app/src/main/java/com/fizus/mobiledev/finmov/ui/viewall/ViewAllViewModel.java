package com.fizus.mobiledev.finmov.ui.viewall;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.paging.ViewAllDataSourceFactory;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;

public class ViewAllViewModel extends ViewModel {

    private final ViewAllDataSourceFactory viewAllDataSourceFactory;
    private LiveData<PagedList<Movie>> moviesLiveData;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public ViewAllViewModel(MovieRepository movieRepository){
        moviesLiveData = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        viewAllDataSourceFactory = new ViewAllDataSourceFactory(movieRepository, compositeDisposable);

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(1)
                .setPageSize(2)
                .build();

        moviesLiveData = new LivePagedListBuilder<>(viewAllDataSourceFactory, pagedConfig)
                .build();
    }

    public LiveData<PagedList<Movie>> getMovies(Movie.Type type, long movieId) {
        viewAllDataSourceFactory.setType(type);
        viewAllDataSourceFactory.setMovieId(movieId);
        return moviesLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
