package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface TopRatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopRatedMovies topRatedMovies);

    @Query("SELECT * FROM top_rated_movies WHERE page LIKE :page")
    Single<TopRatedMovies> getTopRatedMoviesByPage(int page);
}
