package com.fizus.mobiledev.finmov.data.local;

import com.fizus.mobiledev.finmov.utils.converter.MovieTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "top_rated_movies")
public class TopRatedMovies {
    @SerializedName("page")
    @Expose
    @PrimaryKey
    private long page;
    @SerializedName("results")
    @Expose
    @TypeConverters(MovieTypeConverters.class)
    private List<Movie> movies = null;
    @SerializedName("total_results")
    @Expose
    @ColumnInfo(name = "total_result")
    private long totalResults;
    @SerializedName("total_pages")
    @Expose
    @ColumnInfo(name = "total_pages")
    private long totalPages;

    public long getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
