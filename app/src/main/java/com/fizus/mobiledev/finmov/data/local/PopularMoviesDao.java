package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PopularMovies popularMovies);

    @Query("SELECT * FROM popular_movies WHERE page LIKE :page")
    Single<PopularMovies> getPopularMoviesByPage(int page);
}
