package com.fizus.mobiledev.finmov;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.MovieDao;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMovies;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMoviesDao;
import com.fizus.mobiledev.finmov.data.local.RecommendationMovies;
import com.fizus.mobiledev.finmov.data.local.RecommendationMoviesDao;
import com.fizus.mobiledev.finmov.data.local.SimilarMovies;
import com.fizus.mobiledev.finmov.data.local.SimilarMoviesDao;
import com.fizus.mobiledev.finmov.data.local.UpcomingMovies;
import com.fizus.mobiledev.finmov.data.local.UpcomingMoviesDao;

import javax.inject.Singleton;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Singleton
@Database(entities =
        {
                NowPlayingMovies.class,
                UpcomingMovies.class,
                Movie.class,
                RecommendationMovies.class,
                SimilarMovies.class
        }, version = FinmovDatabase.VERSION, exportSchema = false)
public abstract class FinmovDatabase extends RoomDatabase {
    static final int VERSION = 6;

    public abstract MovieDao getMovieDao();

    public abstract NowPlayingMoviesDao getNowPlayingDao();

    public abstract UpcomingMoviesDao getUpcomingMoviesDao();

    public abstract RecommendationMoviesDao getRecommendationMoviesDao();

    public abstract SimilarMoviesDao getSimilarMoviesDao();
}
