package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface SimilarMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SimilarMovies similarMovies);

    @Query("SELECT * FROM similar_movies WHERE id LIKE :movieId AND page LIKE :page")
    Single<SimilarMovies> getSimilarMoviesByMovieId(long movieId, int page);
}
