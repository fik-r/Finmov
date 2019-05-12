package com.fizus.mobiledev.finmov.repository;

import com.fizus.mobiledev.finmov.data.local.CastDao;
import com.fizus.mobiledev.finmov.data.local.CrewDao;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.data.local.MovieDao;
import com.fizus.mobiledev.finmov.data.local.NowPlayingMoviesDao;
import com.fizus.mobiledev.finmov.data.local.PopularMoviesDao;
import com.fizus.mobiledev.finmov.data.local.RecommendationMoviesDao;
import com.fizus.mobiledev.finmov.data.local.SearchResultMovies;
import com.fizus.mobiledev.finmov.data.local.SearchResultMoviesDao;
import com.fizus.mobiledev.finmov.data.local.SimilarMoviesDao;
import com.fizus.mobiledev.finmov.data.local.TopRatedMoviesDao;
import com.fizus.mobiledev.finmov.data.local.UpcomingMoviesDao;
import com.fizus.mobiledev.finmov.data.network.ApiHelper;
import com.fizus.mobiledev.finmov.data.network.responses.CreditsResponseDao;
import com.fizus.mobiledev.finmov.data.network.responses.GetCreditsResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.internal.operators.observable.ObservableAll;

@Singleton
public class MovieRepository {

    private final ApiHelper apiHelper;
    private final MovieDao movieDao;
    private final NowPlayingMoviesDao nowPlayingMoviesDao;
    private final UpcomingMoviesDao upcomingMoviesDao;
    private final PopularMoviesDao popularMoviesDao;
    private final TopRatedMoviesDao topRatedMoviesDao;
    private final SimilarMoviesDao similarMoviesDao;
    private final RecommendationMoviesDao recommendationMoviesDao;
    private final SearchResultMoviesDao searchResultMoviesDao;
    private final CrewDao crewDao;
    private final CastDao castDao;
    private final CreditsResponseDao creditsResponseDao;

    @Inject
    public MovieRepository(
            ApiHelper apiHelper,
            NowPlayingMoviesDao nowPlayingMoviesDao,
            UpcomingMoviesDao upcomingMoviesDao,
            MovieDao movieDao,
            SimilarMoviesDao similarMoviesDao,
            RecommendationMoviesDao recommendationMoviesDao,
            PopularMoviesDao popularMoviesDao,
            TopRatedMoviesDao topRatedMoviesDao,
            SearchResultMoviesDao searchResultMoviesDao,
            CastDao castDao,
            CrewDao crewDao,
            CreditsResponseDao creditsResponseDao) {
        this.apiHelper = apiHelper;
        this.nowPlayingMoviesDao = nowPlayingMoviesDao;
        this.upcomingMoviesDao = upcomingMoviesDao;
        this.movieDao = movieDao;
        this.similarMoviesDao = similarMoviesDao;
        this.recommendationMoviesDao = recommendationMoviesDao;
        this.popularMoviesDao = popularMoviesDao;
        this.topRatedMoviesDao = topRatedMoviesDao;
        this.searchResultMoviesDao = searchResultMoviesDao;
        this.crewDao = crewDao;
        this.castDao = castDao;
        this.creditsResponseDao = creditsResponseDao;
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getNowPlayingMovies(int page, String region) {
        return Observable.concatArrayEager(
                getNowPlayingMoviesFromApi(page, region),
                getNowPlayingMoviesFromDb(page, region)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getUpcomingMovies(int page) {
        return Observable.concatArrayEager(
                getUpcomingMoviesFromApi(page),
                getUpcomingMoviesFromDb(page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getPopularMovies(int page) {
        return Observable.concatArrayEager(
                getPopularMoviesFromApi(page),
                getPopularMoviesFromDb(page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getTopRatedMovies(int page) {
        return Observable.concatArrayEager(
                getTopRatedMoviesFromApi(page),
                getTopRatedMoviesFromDb(page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getSearchResultMovies(String query, int page){
        return Observable.concatArrayEager(
                getSearchResultMoviesFromApi(query, page),
                getSearchResultMoviesFromDb(query, page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<Movie> getDetailMovie(long id) {
        return Single.concatArrayEager(
                getDetailMovieFromApi(id),
                getDetailMovieFromDb(id)
        ).toObservable();
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getSimilarMovies(long movieId, int page) {
        return Observable.concatArrayEager(
                getSimilarMoviesFromApi(movieId, page),
                getSimilarMoviesFromDb(movieId, page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<List<Movie>> getRecommendationMovies(long movieId, int page) {
        return Observable.concatArrayEager(
                getRecommendationMoviesFromApi(movieId, page),
                getRecommendationMoviesFromDb(movieId, page)
        );
    }

    @SuppressWarnings("unchecked")
    public Observable<GetCreditsResponse> getCreditsMovie(long movieId){
        return Single.concatArrayEager(
                getCreditsMovieFromApi(movieId),
                getCreditsMovieFromDb(movieId)
        ).toObservable();
    }
    private Observable<List<Movie>> getNowPlayingMoviesFromApi(int page, String region) {
        return apiHelper.doGetNowPlayingMovies(page, region)
                .flatMapObservable(nowPlayingMovies -> {
                    nowPlayingMovies.setRegion(region);
                    nowPlayingMoviesDao.insert(nowPlayingMovies);
                    return Observable.just(nowPlayingMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getNowPlayingMoviesFromDb(int page, String region) {
        return nowPlayingMoviesDao.getNowPlayingMoviesByPage(page, region)
                .flatMapObservable(nowPlayingMovies -> Observable.just(nowPlayingMovies.getMovies()));
    }

    private Observable<List<Movie>> getUpcomingMoviesFromApi(int page) {
        return apiHelper.doGetUpcomingMovies(page)
                .flatMapObservable(upcomingMovies -> {
                    upcomingMoviesDao.insert(upcomingMovies);
                    return Observable.just(upcomingMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getUpcomingMoviesFromDb(int page) {
        return upcomingMoviesDao.getUpcomingMoviesByPage(page)
                .flatMapObservable(upcomingMovies -> Observable.just(upcomingMovies.getMovies()));
    }

    private Observable<List<Movie>> getPopularMoviesFromApi(int page) {
        return apiHelper.doGetPopularMovies(page)
                .flatMapObservable(popularMovies -> {
                    popularMoviesDao.insert(popularMovies);
                    return Observable.just(popularMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getPopularMoviesFromDb(int page) {
        return popularMoviesDao.getPopularMoviesByPage(page)
                .flatMapObservable(popularMovies -> Observable.just(popularMovies.getMovies()));
    }

    private Observable<List<Movie>> getTopRatedMoviesFromApi(int page) {
        return apiHelper.doGetTopRatedMovies(page)
                .flatMapObservable(topRatedMovies -> {
                    topRatedMoviesDao.insert(topRatedMovies);
                    return Observable.just(topRatedMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getTopRatedMoviesFromDb(int page) {
        return topRatedMoviesDao.getTopRatedMoviesByPage(page)
                .flatMapObservable(topRatedMovies -> Observable.just(topRatedMovies.getMovies()));
    }

    private Observable<List<Movie>> getSearchResultMoviesFromApi(String query, int page){
        return apiHelper.doSearchMoviesByQuery(query, page)
                .flatMapObservable(result -> {
                    result.setName(query);
                    searchResultMoviesDao.insert(result);
                    return Observable.just(result.getMovies());
                });
    }

    private Observable<List<Movie>> getSearchResultMoviesFromDb(String query, int page) {
        return searchResultMoviesDao.getSearchResultMoviesByPage(query, page)
                .flatMapObservable(result -> Observable.just(result.getMovies()));
    }

    private Single<Movie> getDetailMovieFromApi(long id) {
        return apiHelper.doGetDetailMovieById(id)
                .flatMap(movie -> {
                    movieDao.insert(movie);
                    return Single.just(movie);
                });
    }

    private Single<Movie> getDetailMovieFromDb(long id) {
        return movieDao.getMovieById(id);
    }

    private Observable<List<Movie>> getRecommendationMoviesFromApi(long movieId, int page) {
        return apiHelper.doGetRecommendationMoviesByMovieId(movieId, page)
                .flatMapObservable(recommendationMovies -> {
                    recommendationMovies.setId(movieId);
                    recommendationMoviesDao.insert(recommendationMovies);
                    return Observable.just(recommendationMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getRecommendationMoviesFromDb(long movieId, int page) {
        return recommendationMoviesDao.getRecommendationMoviesByMovieId(movieId, page)
                .flatMapObservable(recommendationMovies -> Observable.just(recommendationMovies.getMovies()));
    }

    private Observable<List<Movie>> getSimilarMoviesFromApi(long movieId, int page) {
        return apiHelper.doGetSimilarMoviesByMovieId(movieId, page)
                .flatMapObservable(similarMovies -> {
                    similarMovies.setId(movieId);
                    similarMoviesDao.insert(similarMovies);
                    return Observable.just(similarMovies.getMovies());
                });
    }

    private Observable<List<Movie>> getSimilarMoviesFromDb(long movieId, int page) {
        return similarMoviesDao.getSimilarMoviesByMovieId(movieId, page)
                .flatMapObservable(similarMovies -> Observable.just(similarMovies.getMovies()));
    }

    private Single<GetCreditsResponse> getCreditsMovieFromApi(long movieId) {
        return apiHelper.doGetMovieCreditsCall(movieId)
                .flatMap(creditsResponse -> {
                    creditsResponseDao.insert(creditsResponse);
                    return Single.just(creditsResponse);
                });
    }

    private Single<GetCreditsResponse> getCreditsMovieFromDb(long movieId) {
        return creditsResponseDao.getCreditsResponse(movieId);
    }
}
