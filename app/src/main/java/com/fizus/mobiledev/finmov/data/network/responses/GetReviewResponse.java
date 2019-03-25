package com.fizus.mobiledev.finmov.data.network.responses;

import com.fizus.mobiledev.finmov.data.local.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReviewResponse {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<Review> results = null;
    @SerializedName("total_pages")
    @Expose
    private long totalPages;
    @SerializedName("total_results")
    @Expose
    private long totalResults;

    public long getId() {
        return id;
    }

    public long getPage() {
        return page;
    }

    public List<Review> getResults() {
        return results;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }
}
