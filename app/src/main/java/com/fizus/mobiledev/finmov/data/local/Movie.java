package com.fizus.mobiledev.finmov.data.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.fizus.mobiledev.finmov.utils.converter.GenreTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("poster_path")
    @Expose
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    private long voteCount;
    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average")
    private float voteAverage;
    @SerializedName("budget")
    @Expose
    private long budget;
    @SerializedName("genres")
    @Expose
    @TypeConverters(GenreTypeConverters.class)
    private List<Genre> genres;
    @SerializedName("revenue")
    @Expose
    private long revenue;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("original_language")
    private String originalLanguage;

    public Movie() {

    }

    protected Movie(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readLong();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readLong();
        voteAverage = in.readFloat();
        budget = in.readLong();
        genres = in.createTypedArrayList(Genre.CREATOR);
        revenue = in.readLong();
        status = in.readString();
        runtime = in.readInt();
        originalLanguage = in.readString();
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeLong(voteCount);
        dest.writeFloat(voteAverage);
        dest.writeLong(budget);
        dest.writeTypedList(genres);
        dest.writeLong(revenue);
        dest.writeString(status);
        dest.writeInt(runtime);
        dest.writeString(originalLanguage);
    }

    public enum Type {
        NOW_PLAYING, UPCOMING, TOP_RATED, POPULAR, RECOMMENDATION, SIMILAR
    }

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem == newItem;
        }
    };
}
