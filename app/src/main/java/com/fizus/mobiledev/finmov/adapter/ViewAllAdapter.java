package com.fizus.mobiledev.finmov.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.fizus.mobiledev.finmov.databinding.ItemRowViewallMovieBinding;
import com.fizus.mobiledev.finmov.utils.AppConstans;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllAdapter extends PagedListAdapter<Movie, ViewAllAdapter.ViewAllViewHolder> {

    private static final String TAG = ViewAllAdapter.class.getSimpleName();
    private final Context context;
    private final Callback callback;

    public ViewAllAdapter(Context context, Callback callback) {
        super(Movie.DIFF_CALLBACK);
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemRowViewallMovieBinding itemRowViewallMovieBinding = ItemRowViewallMovieBinding.inflate(layoutInflater, parent, false);
        return new ViewAllViewHolder(itemRowViewallMovieBinding, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllViewHolder holder, int position) {
        holder.bind(Objects.requireNonNull(getItem(position)));
    }

    static class ViewAllViewHolder extends RecyclerView.ViewHolder {
        private ItemRowViewallMovieBinding binding;

        public ViewAllViewHolder(@NonNull ItemRowViewallMovieBinding binding, Callback callback) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> callback.onMovieClick(getAdapterPosition()));
        }

        private void bind(Movie movie) {
            binding.tvMovieTitle.setText(movie.getTitle());

            Glide.with(itemView)
                    .load(AppConstans.BASE_URL_W300 + movie.getPosterPath())
                    .apply(AppConstans.getOptionsDefault())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(binding.ivPoster);
        }
    }
    public interface Callback {
        void onMovieClick(int position);
    }
}

