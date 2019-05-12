package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Single;

@Dao
public interface SearchResultMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchResultMovies searchResultMovies);

    @Query("SELECT * FROM search_result WHERE name LIKE :name AND page LIKE :page")
    Single<SearchResultMovies> getSearchResultMoviesByPage(String name, int page);

}
