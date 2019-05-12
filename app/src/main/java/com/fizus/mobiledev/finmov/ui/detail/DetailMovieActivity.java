package com.fizus.mobiledev.finmov.ui.detail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.adapter.CastCrewAdapter;
import com.fizus.mobiledev.finmov.adapter.GenresAdapter;
import com.fizus.mobiledev.finmov.adapter.ListMovieAdapter;
import com.fizus.mobiledev.finmov.data.local.Cast;
import com.fizus.mobiledev.finmov.data.local.Crew;
import com.fizus.mobiledev.finmov.data.local.Genre;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.databinding.ActivityDetailMovieBinding;
import com.fizus.mobiledev.finmov.utils.AppConstans;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailMovieActivity extends DaggerAppCompatActivity {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    private static final String BASE_URL_W500 = "https://image.tmdb.org/t/p/original";
    private static final String BASE_URL_W300 = "https://image.tmdb.org/t/p/w300";
    private final List<Movie> recommendationMovies = new ArrayList<>();
    private final List<Movie> similarMovies = new ArrayList<>();
    private final List<Genre> genresMovies = new ArrayList<>();
    private final List<Cast> casts = new ArrayList<>();
    private final List<Crew> crews = new ArrayList<>();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ListMovieAdapter recommendationMoviesAdapter;
    private ListMovieAdapter similarMoviesAdapter;
    private CastCrewAdapter crewAdapter;
    private CastCrewAdapter castAdapter;
    private GenresAdapter genresAdapter;
    private DetailMovieViewModel viewModel;
    private ActivityDetailMovieBinding binding;
    private int widthForRecyclerViewMovies;
    private int heightForRecyclerViewMovies;
    private int scrollRange = -1;
    private boolean isShow = false;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMovieViewModel.class);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthForRecyclerViewMovies = displayMetrics.widthPixels;
        heightForRecyclerViewMovies = Math.round(displayMetrics.heightPixels / 4.5f);

        setupGenres();
        setupSimilarMovies();
        setupRecommendationMovies();
        setupCastsMovie();
        setupCrewsMovie();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = extras.getParcelable(AppConstans.EXTRA_MOVIE);
            onBindMovieFirst(movie);
            viewModel.getDetailMovie(movie.getId()).observe(this, this::onBindMovie);
            viewModel.getGenres().observe(this, this::onBindGenres);
            viewModel.getRecommendationMovieLiveData().observe(this, this::onBindRecommendationMovies);
            viewModel.getSimilarMoviesLiveData().observe(this, this::onBindSimilarMovies);
            viewModel.getCastsMovieLiveData().observe(this, this::onBindCastsMovie);
            viewModel.getCrewsMovieLiveData().observe(this, this::onBindCrewsMovie);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupGenres() {
        genresAdapter = new GenresAdapter(this, genresMovies);
        binding.rvMovieGenres.setLayoutManager(new FlexboxLayoutManager(this, FlexDirection.ROW));
        binding.rvMovieGenres.setHasFixedSize(true);
        binding.rvMovieGenres.setAdapter(genresAdapter);
    }

    private void setupCastsMovie(){
        widthForRecyclerViewMovies = displayMetrics.widthPixels;
        heightForRecyclerViewMovies = Math.round(displayMetrics.heightPixels / 3f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(widthForRecyclerViewMovies, heightForRecyclerViewMovies);
        layoutParams.topToBottom = binding.tvCast.getId();
        layoutParams.setMargins(0, 20, 0, 0);
        binding.rvMovieCast.setLayoutParams(layoutParams);
        castAdapter = new CastCrewAdapter(null, casts);
        binding.rvMovieCast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvMovieCast.setHasFixedSize(true);
        binding.rvMovieCast.setAdapter(castAdapter);
    }

    private void setupCrewsMovie(){
        widthForRecyclerViewMovies = displayMetrics.widthPixels;
        heightForRecyclerViewMovies = Math.round(displayMetrics.heightPixels / 3f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(widthForRecyclerViewMovies, heightForRecyclerViewMovies);
        layoutParams.topToBottom = binding.tvCrew.getId();
        layoutParams.setMargins(0, 20, 0, 0);
        binding.rvMovieCrew.setLayoutParams(layoutParams);
        crewAdapter = new CastCrewAdapter(crews, null);
        binding.rvMovieCrew.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvMovieCrew.setHasFixedSize(true);
        binding.rvMovieCrew.setAdapter(crewAdapter);
    }

    private void setupSimilarMovies() {
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(widthForRecyclerViewMovies, heightForRecyclerViewMovies);
        layoutParams.topToBottom = binding.tvSimilar.getId();
        layoutParams.setMargins(0, 20, 0, 0);
        binding.rvMovieSimilar.setLayoutParams(layoutParams);
        similarMoviesAdapter = new ListMovieAdapter(this, similarMovies, false,
                position -> startDetailMovieActivity(similarMovies.get(position))
        );
        binding.rvMovieSimilar.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvMovieSimilar.setHasFixedSize(true);
        binding.rvMovieSimilar.setAdapter(similarMoviesAdapter);
    }

    private void setupRecommendationMovies() {
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(widthForRecyclerViewMovies, heightForRecyclerViewMovies);
        layoutParams.topToBottom = binding.tvRecommendation.getId();
        layoutParams.setMargins(0, 20, 0, 0);
        binding.rvMovieRecommendation.setLayoutParams(layoutParams);
        recommendationMoviesAdapter = new ListMovieAdapter(this, recommendationMovies, false,
                position -> startDetailMovieActivity(recommendationMovies.get(position))
        );
        binding.rvMovieRecommendation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvMovieRecommendation.setHasFixedSize(true);
        binding.rvMovieRecommendation.setAdapter(recommendationMoviesAdapter);
    }

    private void onBindGenres(List<Genre> genres) {
        genresMovies.clear();
        genresMovies.addAll(genres);
        genresAdapter.notifyDataSetChanged();
    }

    private void onBindSimilarMovies(List<Movie> movies) {
        if(movies.size() <= 0) {
            binding.rvMovieSimilar.setVisibility(View.GONE);
            binding.tvSimilar.setVisibility(View.GONE);
            return;
        }
        Log.e(TAG, "onBindSimilarMovies: " + movies.size());
        this.similarMovies.clear();
        this.similarMovies.addAll(movies);
        similarMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindRecommendationMovies(List<Movie> movies) {
        if(movies.size() <= 0) {
            binding.rvMovieRecommendation.setVisibility(View.GONE);
            binding.tvRecommendation.setVisibility(View.GONE);
            return;
        }
        Log.e(TAG, "onBindRecommendationMovies: " + movies.size());
        this.recommendationMovies.clear();
        this.recommendationMovies.addAll(movies);
        recommendationMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindMovieFirst(Movie movie) {
        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (scrollRange == -1) {
                scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (scrollRange + verticalOffset == 0) {
                isShow = false;
                binding.collapsingtoolbar.setTitle(movie.getTitle());
                binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
            } else if (!isShow) {
                binding.collapsingtoolbar.setTitle(" ");
                binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
                isShow = true;
            }
        });
        Glide.with(this)
                .load(BASE_URL_W300 + movie.getPosterPath())
                .apply(AppConstans.getOptionsDefault())
                .into(binding.ivPoster);
        Glide.with(this)
                .load(BASE_URL_W500 + movie.getBackdropPath())
                .apply(AppConstans.getOptionsDefault())
                .into(binding.ivBackdrop);
        binding.tvMovieName.setText(movie.getTitle());
        binding.ratingMovie.setRating(movie.getVoteAverage());
        binding.tvMovieOriginalLanguage.setText(movie.getOriginalLanguage());
        binding.tvMovieReleaseDate.setText(viewModel.getFormattedDate(movie.getReleaseDate()));
        binding.tvMovieVoteCount.setText(viewModel.getFormattedVoteCount(movie.getVoteCount()));
        binding.tvMovieOverview.setText(movie.getOverview());
    }

    private void onBindMovie(Movie movie) {
        binding.tvMovieStatus.setText(String.valueOf(movie.getStatus()));
        binding.tvMovieRuntime.setText(viewModel.convertMinutesToHours(movie.getRuntime()));
        binding.tvMovieBudget.setText(viewModel.getFormattedCurrency(movie.getBudget()));
        binding.tvMovieRevenue.setText(viewModel.getFormattedCurrency(movie.getRevenue()));
    }

    private void onBindCastsMovie(List<Cast> casts){
        if(casts.size() <= 0) {
            binding.rvMovieCast.setVisibility(View.GONE);
            binding.tvCast.setVisibility(View.GONE);
            return;
        }
        String cast = getString(R.string.cast);
        binding.tvCast.setText(cast + " (" + casts.size() + ")");
        this.casts.clear();
        this.casts.addAll(casts);
        this.castAdapter.notifyDataSetChanged();
    }

    private void onBindCrewsMovie(List<Crew> crews){
        if(crews.size() <= 0) {
            binding.rvMovieCrew.setVisibility(View.GONE);
            binding.tvCrew.setVisibility(View.GONE);
            return;
        }
        String crew = getString(R.string.crew);
        binding.tvCrew.setText(crew + " (" + crews.size() + ")" );
        this.crews.clear();
        this.crews.addAll(crews);
        this.crewAdapter.notifyDataSetChanged();
    }

    private void startDetailMovieActivity(Movie movie) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(AppConstans.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
