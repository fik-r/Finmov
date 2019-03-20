package com.fizus.mobiledev.finmov.data.local;

import com.fizus.mobiledev.finmov.utils.MovieTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "similar_movies", indices = {@Index(value = {"page"}, unique = true)})
public class SimilarMovies {

    @PrimaryKey
    private long id;
    @SerializedName("page")
    @Expose
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
