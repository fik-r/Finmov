package com.fizus.mobiledev.finmov;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.MovieDao;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMovies;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMoviesDao;
import com.fizus.mobiledev.finmov.data.local.PopularMovies;
import com.fizus.mobiledev.finmov.data.local.PopularMoviesDao;
import com.fizus.mobiledev.finmov.data.local.RecommendationMovies;
import com.fizus.mobiledev.finmov.data.local.RecommendationMoviesDao;
import com.fizus.mobiledev.finmov.data.local.SimilarMovies;
import com.fizus.mobiledev.finmov.data.local.SimilarMoviesDao;
import com.fizus.mobiledev.finmov.data.local.TopRatedMovies;
import com.fizus.mobiledev.finmov.data.local.TopRatedMoviesDao;
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
                PopularMovies.class,
                TopRatedMovies.class,
                Movie.class,
                RecommendationMovies.class,
                SimilarMovies.class
        }, version = FinmovDatabase.VERSION, exportSchema = false)
public abstract class FinmovDatabase extends RoomDatabase {
    static final int VERSION = 1;

    public abstract MovieDao getMovieDao();

    public abstract NowPlayingMoviesDao getNowPlayingDao();

    public abstract UpcomingMoviesDao getUpcomingMoviesDao();

    public abstract PopularMoviesDao getPopularMoviesDao();

    public abstract TopRatedMoviesDao getTopRatedMoviesDao();

    public abstract RecommendationMoviesDao getRecommendationMoviesDao();

    public abstract SimilarMoviesDao getSimilarMoviesDao();
}
