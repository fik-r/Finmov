package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface NowPlayingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NowPlayingMovies nowPlayingMovies);

    @Query("SELECT * FROM now_playing_movies WHERE page LIKE :page AND region LIKE :region")
    Single<NowPlayingMovies> getNowPlayingMoviesByPage(int page, String region);
}
