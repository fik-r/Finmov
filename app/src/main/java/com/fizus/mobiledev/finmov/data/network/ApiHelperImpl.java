package com.fizus.mobiledev.finmov.data.network;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMovies;
import com.fizus.mobiledev.finmov.data.local.PopularMovies;
import com.fizus.mobiledev.finmov.data.local.RecommendationMovies;
import com.fizus.mobiledev.finmov.data.local.SimilarMovies;
import com.fizus.mobiledev.finmov.data.local.TopRatedMovies;
import com.fizus.mobiledev.finmov.data.local.UpcomingMovies;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ApiHelperImpl implements ApiHelper {

    private final String apiKey;
    private final ApiService client;

    @Inject
    public ApiHelperImpl(@Named("API_KEY") String apiKey, ApiService client) {
        this.apiKey = apiKey;
        this.client = client;
    }

    @Override
    public Single<NowPlayingMovies> doGetNowPlayingMovies(int page, String region) {
        return client.doGetNowPlayingMovies(apiKey, page, region);
    }

    @Override
    public Single<UpcomingMovies> doGetUpcomingMovies(int page) {
        return client.doGetUpcomingMovies(apiKey, page);
    }

    @Override
    public Single<Movie> doGetDetailMovieById(long id) {
        return client.doGetMovieDetailCall(id, apiKey);
    }

    @Override
    public Single<RecommendationMovies> doGetRecommendationMoviesByMovieId(long movieId, int page) {
        return client.doGetRecommendationMoviesCall(movieId, apiKey, page);
    }

    @Override
    public Single<SimilarMovies> doGetSimilarMoviesByMovieId(long movieId, int page) {
        return client.doGetSimilarMoviesCall(movieId, apiKey, page);
    }

    @Override
    public Single<PopularMovies> doGetPopularMovies(int page) {
        return client.doGetPopularMovies(apiKey, page);
    }

    @Override
    public Single<TopRatedMovies> doGetTopRatedMovies(int page) {
        return client.doGetTopRatedMovies(apiKey, page);
    }

}
