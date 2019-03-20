package com.fizus.mobiledev.finmov.di.modules;

import android.app.Application;

import com.fizus.mobiledev.finmov.FinmovDatabase;
import com.fizus.mobiledev.finmov.data.local.MovieDao;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMoviesDao;
import com.fizus.mobiledev.finmov.data.local.RecommendationMoviesDao;
import com.fizus.mobiledev.finmov.data.local.SimilarMoviesDao;
import com.fizus.mobiledev.finmov.data.local.UpcomingMoviesDao;
import com.fizus.mobiledev.finmov.data.network.ApiHelper;
import com.fizus.mobiledev.finmov.repository.MovieRepository;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    FinmovDatabase provideFinmovDatabase(Application application) {
        return Room.databaseBuilder(application, FinmovDatabase.class, "finmov")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(FinmovDatabase finmovDatabase) {
        return finmovDatabase.getMovieDao();
    }

    @Singleton
    @Provides
    NowPlayingMoviesDao provideNowPlayingDao(FinmovDatabase finmovDatabase) {
        return finmovDatabase.getNowPlayingDao();
    }

    @Singleton
    @Provides
    UpcomingMoviesDao provideUpcomingMoviesDao(FinmovDatabase finmovDatabase) {
        return finmovDatabase.getUpcomingMoviesDao();
    }

    @Singleton
    @Provides
    SimilarMoviesDao provideSimilarMoviesDao(FinmovDatabase finmovDatabase) {
        return finmovDatabase.getSimilarMoviesDao();
    }

    @Singleton
    @Provides
    RecommendationMoviesDao provideRecommendationDao(FinmovDatabase finmovDatabase) {
        return finmovDatabase.getRecommendationMoviesDao();
    }

    @Singleton
    @Provides
    MovieRepository provideMovieRepository(
            ApiHelper apiHelper,
            NowPlayingMoviesDao nowPlayingMoviesDao,
            UpcomingMoviesDao upcomingMoviesDao,
            MovieDao movieDao,
            RecommendationMoviesDao recommendationMoviesDao,
            SimilarMoviesDao similarMoviesDao
    ) {
        return new MovieRepository(
                apiHelper,
                nowPlayingMoviesDao,
                upcomingMoviesDao,
                movieDao,
                similarMoviesDao,
                recommendationMoviesDao
        );
    }

}
