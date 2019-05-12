package com.fizus.mobiledev.finmov.data.network;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMovies;
import com.fizus.mobiledev.finmov.data.local.PopularMovies;
import com.fizus.mobiledev.finmov.data.local.RecommendationMovies;
import com.fizus.mobiledev.finmov.data.local.SearchResultMovies;
import com.fizus.mobiledev.finmov.data.local.SimilarMovies;
import com.fizus.mobiledev.finmov.data.local.TopRatedMovies;
import com.fizus.mobiledev.finmov.data.local.UpcomingMovies;
import com.fizus.mobiledev.finmov.data.network.responses.GetCreditsResponse;
import com.fizus.mobiledev.finmov.data.network.responses.GetGenresResponse;
import com.fizus.mobiledev.finmov.data.network.responses.GetReviewResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("3/movie/now_playing")
    Single<NowPlayingMovies> doGetNowPlayingMovies(
            @Query("api_key") String ApiKey,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET("3/movie/upcoming")
    Single<UpcomingMovies> doGetUpcomingMovies(
            @Query("api_key") String ApiKey,
            @Query("page") int page
    );

    @GET("3/movie/popular")
    Single<PopularMovies> doGetPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/movie/top_rated")
    Single<TopRatedMovies> doGetTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}/credits")
    Single<GetCreditsResponse> doGetMovieCreditsCall(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey
    );

    @GET("3/movie/{movie_id}")
    Single<Movie> doGetMovieDetailCall(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey
    );

    @GET("3/movie/{movie_id}/similar")
    Single<SimilarMovies> doGetSimilarMoviesCall(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}/recommendations")
    Single<RecommendationMovies> doGetRecommendationMoviesCall(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}/reviews")
    Single<GetReviewResponse> doGetReviewCall(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/genre/movie/list")
    Single<GetGenresResponse> doGetGenresCall(
            @Query("api_key") String apiKey
    );

    @GET("3/search/movie")
    Single<SearchResultMovies> doSearchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("3/discover/movie")
    Single<NowPlayingMovies> doFindMovieByGenre(
            @Query("api_key") String apiKey,
            @Query("with_genres") String genre,
            @Query("page") int page
    );
}
