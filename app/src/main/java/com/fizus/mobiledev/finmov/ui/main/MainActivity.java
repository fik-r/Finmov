package com.fizus.mobiledev.finmov.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.adapter.CountryAdapter;
import com.fizus.mobiledev.finmov.adapter.ListMovieAdapter;
import com.fizus.mobiledev.finmov.data.local.Country;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.databinding.ActivityMainBinding;
import com.fizus.mobiledev.finmov.ui.detail.DetailMovieActivity;
import com.fizus.mobiledev.finmov.utils.CountryUtil;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel = null;
    private ActivityMainBinding binding = null;
    private ListMovieAdapter nowPlayingMoviesAdapter = null;
    private ListMovieAdapter upcomingMoviesAdapter = null;
    private ListMovieAdapter popularMoviesAdapter = null;
    private ListMovieAdapter topRatedMoviesAdapter = null;

    private final List<Movie> nowPlayingMovies = new ArrayList<>();
    private final List<Movie> upcomingMovies = new ArrayList<>();
    private final List<Movie> popularMovies = new ArrayList<>();
    private final List<Movie> topRatedMovies = new ArrayList<>();

    private DisplayMetrics displayMetrics = null;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

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

        viewModel.getUpcomingMovies(1).observe(this, this::onBindUpcomingMovies);
        viewModel.getPopularMoviesLiveData(1).observe(this, this::onBindPopularMovies);
        viewModel.getTopRatedMoviesLiveData(1).observe(this, this::onBindTopRatedMovies);
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

    private void setupPopularMovies(){
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

    private void setupTopRatedMovies(){
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

    private void onBindPopularMovies(List<Movie> movies){
        Log.e(TAG, "onBindPopularMovies: " + movies.size());
        this.popularMovies.clear();
        this.popularMovies.addAll(movies);
        popularMoviesAdapter.notifyDataSetChanged();
    }

    private void onBindTopRatedMovies(List<Movie> movies){
        Log.e(TAG, "onBindTopRatedMovies: " + movies.size());
        this.topRatedMovies.clear();
        this.topRatedMovies.addAll(movies);
        topRatedMoviesAdapter.notifyDataSetChanged();
    }

    private void startDetailMovieActivity(Movie movie) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(Movie.TAG, movie);
        startActivity(intent);
    }
}
