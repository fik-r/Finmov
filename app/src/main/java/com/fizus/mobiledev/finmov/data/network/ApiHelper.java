package com.fizus.mobiledev.finmov.data.network;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMovies;
import com.fizus.mobiledev.finmov.data.local.PopularMovies;
import com.fizus.mobiledev.finmov.data.local.RecommendationMovies;
import com.fizus.mobiledev.finmov.data.local.SimilarMovies;
import com.fizus.mobiledev.finmov.data.local.TopRatedMovies;
import com.fizus.mobiledev.finmov.data.local.UpcomingMovies;

import io.reactivex.Single;

public interface ApiHelper {
    Single<NowPlayingMovies> doGetNowPlayingMovies(int page, String region);

    Single<UpcomingMovies> doGetUpcomingMovies(int page);

    Single<Movie> doGetDetailMovieById(long id);

    Single<RecommendationMovies> doGetRecommendationMoviesByMovieId(long movieId, int page);

    Single<SimilarMovies> doGetSimilarMoviesByMovieId(long movieId, int page);

    Single<PopularMovies> doGetPopularMovies(int page);

    Single<TopRatedMovies> doGetTopRatedMovies(int page);
}
