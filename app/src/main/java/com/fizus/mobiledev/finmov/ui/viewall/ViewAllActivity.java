package com.fizus.mobiledev.finmov.ui.viewall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.adapter.ViewAllAdapter;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.databinding.ActivityViewallBinding;
import com.fizus.mobiledev.finmov.ui.detail.DetailMovieActivity;
import com.fizus.mobiledev.finmov.utils.AppConstans;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import dagger.android.support.DaggerAppCompatActivity;

public class ViewAllActivity extends DaggerAppCompatActivity {

    private static final String TAG = ViewAllActivity.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ActivityViewallBinding binding = null;
    private ViewAllViewModel viewModel = null;
    private ViewAllAdapter adapter = null;
    private PagedList<Movie> movies = null;
    private String title = "";
    private Movie.Type type = null;
    private long movieId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewAllViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewall);

        if (getIntent().getExtras() != null) {
            movieId = getIntent().getExtras().getLong(AppConstans.EXTRA_MOVIE);
            type = (Movie.Type) getIntent().getExtras().getSerializable(AppConstans.EXTRA_MOVIE_TYPE);
            switch (type) {
                case UPCOMING:
                    title = getString(R.string.upcoming_movies);
                    break;
                case POPULAR:
                    title = getString(R.string.popular_movies);
                    break;
                case TOP_RATED:
                    title = getString(R.string.top_rated_movies);
                    break;
                case SIMILAR:
                    title = getString(R.string.similar_movies);
                    break;
                case RECOMMENDATION:
                    title = getString(R.string.recommendation_movies);
                    break;
            }
        }
        Log.e(TAG, "onCreate: " + title );
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupMovies();

        viewModel.getMovies(type, movieId).observe(this, this::onBindMovies);
    }

    private void setupMovies() {
        adapter = new ViewAllAdapter(this, position -> startDetailMovieActivity(movies.get(position)));
        binding.rvMovie.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovie.setAdapter(adapter);
    }

    private void onBindMovies(PagedList<Movie> movies) {
        this.movies = movies;
        adapter.submitList(movies);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void startDetailMovieActivity(Movie movie) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(AppConstans.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
