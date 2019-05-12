package com.fizus.mobiledev.finmov.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.adapter.CountryAdapter;
import com.fizus.mobiledev.finmov.adapter.ListMovieAdapter;
import com.fizus.mobiledev.finmov.adapter.ViewAllAdapter;
import com.fizus.mobiledev.finmov.data.local.Country;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.databinding.ActivityMainBinding;
import com.fizus.mobiledev.finmov.ui.detail.DetailMovieActivity;
import com.fizus.mobiledev.finmov.ui.viewall.ViewAllActivity;
import com.fizus.mobiledev.finmov.utils.AppConstans;
import com.fizus.mobiledev.finmov.utils.CountryUtil;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final List<Movie> nowPlayingMovies = new ArrayList<>();
    private final List<Movie> upcomingMovies = new ArrayList<>();
    private final List<Movie> popularMovies = new ArrayList<>();
    private final List<Movie> topRatedMovies = new ArrayList<>();
    private PagedList<Movie> searchResult = null;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel viewModel = null;
    private ActivityMainBinding binding = null;
    private ListMovieAdapter nowPlayingMoviesAdapter = null;
    private ListMovieAdapter upcomingMoviesAdapter = null;
    private ListMovieAdapter popularMoviesAdapter = null;
    private ListMovieAdapter topRatedMoviesAdapter = null;
    private ViewAllAdapter searchResultAdapter = null;
    private DisplayMetrics displayMetrics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        setupMoviesIn();
        setupNowPlayingMovies();
        setupUpcomingMovies();
        setupPopularMovies();
        setupTopRatedMovies();
        setupSearchView();
        setupResult();

        viewModel.getUpcomingMovies(1).observe(this, this::onBindUpcomingMovies);
        viewModel.getPopularMoviesLiveData(1).observe(this, this::onBindPopularMovies);
        viewModel.getTopRatedMoviesLiveData(1).observe(this, this::onBindTopRatedMovies);
//        viewModel.getSearchResultLiveData().observe(this, this::onBindResult);

        binding.tvViewAllUpcomingMovies.setOnClickListener(v -> startViewAllActivity(Movie.Type.UPCOMING));
        binding.tvViewAllPopularMovies.setOnClickListener(v -> startViewAllActivity(Movie.Type.POPULAR));
        binding.tvViewAllTopratedMovies.setOnClickListener(v -> startViewAllActivity(Movie.Type.TOP_RATED));
    }

    private void setupSearchView(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.onQueryTextChange(newText).observe(MainActivity.this, MainActivity.this::onBindResult);
                return false;
            }
        });
        binding.searchView.setOnSearchClickListener(v -> {

        });
        binding.searchView.setOnCloseListener(() -> {
            searchResult.detach();
            searchResultAdapter.notifyDataSetChanged();
            binding.resultLayout.setVisibility(View.GONE);
            return false;
        });
    }
    private void setupMoviesIn() {
        List<Country> countries = CountryUtil.getCountries();
        CountryAdapter countryAdapter = new CountryAdapter(countries);
        binding.spMoviesIn.setAdapter(countryAdapter);
        binding.spMoviesIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String region = countries.get(position).getCountryCode();
                viewModel.getNowPlayingMovies(1, region).observe(MainActivity.this, MainActivity.this::onBindNowPlayingMovies);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupNowPlayingMovies() {
        int widthForCard = Math.round(displayMetrics.widthPixels / 1.5f);
        int heightForRecyclerView = Math.round(displayMetrics.heightPixels / 1.5f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(displayMetrics.widthPixels, heightForRecyclerView);
        layoutParams.topToBottom = binding.tvMoviesIn.getId();
        binding.rvNowShowing.setLayoutParams(layoutParams);
        nowPlayingMoviesAdapter = new ListMovieAdapter(this, nowPlayingMovies, true,
                position -> startDetailMovieActivity(nowPlayingMovies.get(position))
        );
        binding.rvNowShowing.setLayoutManager(new CardSliderLayoutManager(50, widthForCard, 16));
        binding.rvNowShowing.setHasFixedSize(true);
        binding.rvNowShowing.setAdapter(nowPlayingMoviesAdapter);
        new CardSnapHelper().attachToRecyclerView(binding.rvNowShowing);
    }

    private void setupUpcomingMovies() {
        int heightForRecyclerView = Math.round(displayMetrics.heightPixels / 4.5f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(displayMetrics.widthPixels, heightForRecyclerView);
        layoutParams.topToBottom = binding.tvUpcomingMovies.getId();
        layoutParams.setMargins(0, 30, 0, 0);
        binding.rvUpcomingMovies.setLayoutParams(layoutParams);
        upcomingMoviesAdapter = new ListMovieAdapter(this, upcomingMovies, false,
                position -> startDetailMovieActivity(upcomingMovies.get(position))
        );
        binding.rvUpcomingMovies.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvUpcomingMovies.setHasFixedSize(true);
        binding.rvUpcomingMovies.setAdapter(upcomingMoviesAdapter);
    }

    private void setupPopularMovies() {
        int heightForRecyclerView = Math.round(displayMetrics.heightPixels / 4.5f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(displayMetrics.widthPixels, heightForRecyclerView);
        layoutParams.topToBottom = binding.tvPopularMovies.getId();
        layoutParams.setMargins(0, 30, 0, 0);
        binding.rvPopularMovies.setLayoutParams(layoutParams);
        popularMoviesAdapter = new ListMovieAdapter(this, popularMovies, false,
                position -> startDetailMovieActivity(popularMovies.get(position))
        );
        binding.rvPopularMovies.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvPopularMovies.setHasFixedSize(true);
        binding.rvPopularMovies.setAdapter(popularMoviesAdapter);
    }

    private void setupTopRatedMovies() {
        int heightForRecyclerView = Math.round(displayMetrics.heightPixels / 4.5f);
        ConstraintLayout.LayoutParams layoutParams = new Constraints.LayoutParams(displayMetrics.widthPixels, heightForRecyclerView);
        layoutParams.topToBottom = binding.tvTopratedMovies.getId();
        layoutParams.setMargins(0, 30, 0, 0);
        binding.rvTopratedMovies.setLayoutParams(layoutParams);
        topRatedMoviesAdapter = new ListMovieAdapter(this, topRatedMovies, false,
                position -> startDetailMovieActivity(topRatedMovies.get(position))
        );
        binding.rvTopratedMovies.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvTopratedMovies.setHasFixedSize(true);
        binding.rvTopratedMovies.setAdapter(topRatedMoviesAdapter);
    }

    private void setupResult() {
        searchResultAdapter = new ViewAllAdapter(this, position -> startDetailMovieActivity(searchResult.get(position)));
        binding.rvResult.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvResult.setHasFixedSize(true);
        binding.rvResult.setAdapter(searchResultAdapter);
    }

    private void onBindNowPlayingMovies(List<Movie> movies) {
        Log.e(TAG, "onBindNowPlayingMovies: " + movies.size());
        this.nowPlayingMovies.clear();
        this.nowPlayingMovies.addAll(movies);
        binding.rvNowShowing.scrollToPosition(0);
        nowPlayingMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindUpcomingMovies(List<Movie> movies) {
        Log.e(TAG, "onBindUpcomingMovies: " + movies.size());
        this.upcomingMovies.clear();
        this.upcomingMovies.addAll(movies);
        upcomingMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindPopularMovies(List<Movie> movies) {
        Log.e(TAG, "onBindPopularMovies: " + movies.size());
        this.popularMovies.clear();
        this.popularMovies.addAll(movies);
        popularMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindTopRatedMovies(List<Movie> movies) {
        Log.e(TAG, "onBindTopRatedMovies: " + movies.size());
        this.topRatedMovies.clear();
        this.topRatedMovies.addAll(movies);
        topRatedMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindResult(PagedList<Movie> result) {
        if (result != null) {
            Log.e(TAG, "onBindResult: " + result.size() );
            binding.resultLayout.setVisibility(View.VISIBLE);
            this.searchResult = result;
            searchResultAdapter.submitList(searchResult);
        }
    }

    private void startDetailMovieActivity(Movie movie) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(AppConstans.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    private void startViewAllActivity(Movie.Type type) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        intent.putExtra(AppConstans.EXTRA_MOVIE_TYPE, type);
        startActivity(intent);
    }
}
