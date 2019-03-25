package com.fizus.mobiledev.finmov.data.network.responses;

import com.fizus.mobiledev.finmov.data.local.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetGenresResponse {
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    public List<Genre> getGenres() {
        return genres;
    }
}
