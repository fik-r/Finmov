package com.fizus.mobiledev.finmov.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.utils.AppConstans;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListMovieViewHolder> {

    private final List<Movie> movies;
    private Context context;
    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOWSHOWING = 2;
    private final boolean isNowShowing;
    private final Callback callback;

    private static final String TAG = ListMovieAdapter.class.getSimpleName();

    public ListMovieAdapter(Context context, List<Movie> movies, boolean isNowShowing, Callback callback) {
        this.context = context;
        this.movies = movies;
        this.isNowShowing = isNowShowing;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ListMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_NOWSHOWING:
                view = LayoutInflater.from(context).inflate(R.layout.item_row_now_playing_movie, parent, false);
                break;
            case VIEW_TYPE_NORMAL:
                view = LayoutInflater.from(context).inflate(R.layout.item_row_movie, parent, false);
                int height = parent.getMeasuredHeight();
                int width = Math.round(parent.getMeasuredWidth() / 4f);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, height);
                layoutParams.setMargins(15, 0, 15, 0);
                view.setLayoutParams(layoutParams);
                break;
            case VIEW_TYPE_EMPTY:
                view = LayoutInflater.from(context).inflate(R.layout.item_row_empty_movie, parent, false);
                break;
        }

        return new ListMovieViewHolder(view, context, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieViewHolder holder, int position) {
        holder.bind(movies.get(position), isNowShowing);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.size() > 0) {
            if (isNowShowing) return VIEW_TYPE_NOWSHOWING;
            else return VIEW_TYPE_NORMAL;
        } else return VIEW_TYPE_EMPTY;
    }

    static class ListMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private SpinKitView loading;
        private Context context;

        ListMovieViewHolder(@NonNull View itemView, Context context, Callback callback) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_poster);
            loading = itemView.findViewById(R.id.loading);

            this.context = context;
            itemView.setOnClickListener(v -> callback.onMovieClick(getAdapterPosition()));
        }

        private void bind(Movie movie, boolean isNowShowing) {
            float densityDpi = context.getResources().getDisplayMetrics().densityDpi;
            int roundingRadius;

            if (isNowShowing) roundingRadius = Math.round(densityDpi / 5.6f);
            else roundingRadius = Math.round(densityDpi / 16.8f);

            RequestOptions options = new RequestOptions()
                    .transform(new RoundedCorners(roundingRadius))
                    .error(R.drawable.placeholder);

            Glide.with(itemView)
                    .load((isNowShowing ? AppConstans.BASE_URL_W500 : AppConstans.BASE_URL_W300) + movie.getPosterPath())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e(TAG, "onLoadFailed: " );
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.e(TAG, "onResourceReady: " );
                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(options)
                    .into(ivPoster);
        }
    }

    public interface Callback {
        void onMovieClick(int position);
    }
}
