package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface RecommendationMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecommendationMovies recommendationMovies);

    @Query("SELECT * FROM recommendation_movies WHERE id LIKE :moviesId AND page LIKE :page")
    Single<RecommendationMovies> getRecommendationMoviesByMovieId(long moviesId, int page);
}
