package com.fizus.mobiledev.finmov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.data.local.Genre;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder> {

    private final Context context;
    private final List<Genre> genres;

    public GenresAdapter(Context context, List<Genre> genres) {
        this.context = context;
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_genre, parent, false);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
        holder.onBind(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class GenresViewHolder extends RecyclerView.ViewHolder {

        private MaterialButton btnGenre;

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);

            btnGenre = itemView.findViewById(R.id.btn_genre);
        }

        void onBind(Genre genre) {
            btnGenre.setText(genre.getName());
        }
    }
}
