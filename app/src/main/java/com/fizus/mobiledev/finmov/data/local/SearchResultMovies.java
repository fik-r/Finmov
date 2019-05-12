package com.fizus.mobiledev.finmov.data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fizus.mobiledev.finmov.utils.converter.MovieTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "search_result", indices = {@Index(value = {"page"}, unique = true)})
public class SearchResultMovies {

    @PrimaryKey
    @NonNull
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
