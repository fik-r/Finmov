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

import io.reactivex.Single;

public interface ApiHelper {
    Single<NowPlayingMovies> doGetNowPlayingMovies(int page, String region);

    Single<UpcomingMovies> doGetUpcomingMovies(int page);

    Single<Movie> doGetDetailMovieById(long id);

    Single<RecommendationMovies> doGetRecommendationMoviesByMovieId(long movieId, int page);

    Single<SimilarMovies> doGetSimilarMoviesByMovieId(long movieId, int page);

    Single<PopularMovies> doGetPopularMovies(int page);

    Single<TopRatedMovies> doGetTopRatedMovies(int page);

    Single<GetCreditsResponse> doGetMovieCreditsCall(long movieId);

    Single<SearchResultMovies> doSearchMoviesByQuery(String query, int page);
}
