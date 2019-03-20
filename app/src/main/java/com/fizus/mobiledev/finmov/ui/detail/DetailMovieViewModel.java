package com.fizus.mobiledev.finmov.ui.detail;

import android.annotation.SuppressLint;
import android.util.Log;

import com.fizus.mobiledev.finmov.data.local.Genre;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.repository.MovieRepository;
import com.fizus.mobiledev.finmov.utils.SchedulerProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class DetailMovieViewModel extends ViewModel {
    private static final String TAG = DetailMovieViewModel.class.getSimpleName();
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final MovieRepository movieRepository;
    private final MutableLiveData<Movie> movieLiveData;
    private final MutableLiveData<List<Movie>> similarMoviesLiveData;
    private final MutableLiveData<List<Movie>> recommendationMovieLiveData;
    private final MutableLiveData<List<Genre>> genresLiveData;

    @Inject
    public DetailMovieViewModel(MovieRepository movieRepository, SchedulerProvider schedulerProvider) {
        this.movieRepository = movieRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.movieLiveData = new MutableLiveData<>();
        this.similarMoviesLiveData = new MutableLiveData<>();
        this.recommendationMovieLiveData = new MutableLiveData<>();
        this.genresLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public MutableLiveData<Movie> getDetailMovie(long id) {
        compositeDisposable.add(Observable.zip(
                movieRepository.getDetailMovie(id)
                        .subscribeOn(schedulerProvider.io())
                        .debounce(400, TimeUnit.MILLISECONDS),
                movieRepository.getRecommendationMovies(id, 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .debounce(400, TimeUnit.MILLISECONDS),
                movieRepository.getSimilarMovies(id, 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .debounce(400, TimeUnit.MILLISECONDS),
                (detailMovie, recommendationMovies, similarMovies) -> {
                    similarMoviesLiveData.postValue(similarMovies);
                    recommendationMovieLiveData.postValue(recommendationMovies);
                    return detailMovie;
                })
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(movie -> {
                    genresLiveData.postValue(movie.getGenres());
                    movieLiveData.postValue(movie);
                }, throwable -> Log.e(TAG, "getDetailMovie: " + throwable.getLocalizedMessage())));
        return movieLiveData;
    }

    public MutableLiveData<List<Genre>> getGenres(){
        return genresLiveData;
    }
    public MutableLiveData<List<Movie>> getSimilarMoviesLiveData() {
        return similarMoviesLiveData;
    }

    public MutableLiveData<List<Movie>> getRecommendationMovieLiveData() {
        return recommendationMovieLiveData;
    }

    public String convertMinutesToHours(int minutes){
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return hours + "h " + remainMinutes + "m";
    }

    @SuppressLint("DefaultLocale")
    public String getFormattedCurrency(long money){
        return String.format("%s%,.2f", "$", (float) money);
    }

    @SuppressLint("DefaultLocale")
    public String getFormattedVoteCount(long voteCount){
        return String.format("%,d", voteCount);
    }

    public String getFormattedDate(String date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("id", "IDN"));

        return simpleDateFormat.format(formattedDate);
    }
}
