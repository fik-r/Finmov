package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface UpcomingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UpcomingMovies upcomingMovies);

    @Query("SELECT * FROM upcoming_movies WHERE page LIKE :page")
    Single<UpcomingMovies> getUpcomingMoviesByPage(int page);
}
