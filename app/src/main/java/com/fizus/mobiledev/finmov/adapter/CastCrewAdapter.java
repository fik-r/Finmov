package com.fizus.mobiledev.finmov.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.data.local.Cast;
import com.fizus.mobiledev.finmov.data.local.Crew;
import com.fizus.mobiledev.finmov.databinding.ItemRowCastCrewBinding;
import com.fizus.mobiledev.finmov.utils.AppConstans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CastCrewAdapter extends RecyclerView.Adapter<CastCrewAdapter.CastCrewViewHolder> {

    private final List<Crew> crews;
    private final List<Cast> casts;

    public CastCrewAdapter(List<Crew> crews, List<Cast> casts) {
        this.crews = crews;
        this.casts = casts;
    }

    @NonNull
    @Override
    public CastCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowCastCrewBinding binding = ItemRowCastCrewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        int height = parent.getMeasuredHeight();
//        int width = Math.round(parent.getMeasuredWidth() / 4f);
//        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, height);
//        layoutParams.setMargins(15, 0, 15, 0);
//        binding.getRoot().setLayoutParams(layoutParams);
        return new CastCrewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastCrewViewHolder holder, int position) {
        if(crews != null)
            holder.bind(crews.get(position));
        if(casts != null)
            holder.bind(casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts != null ? casts.size() : crews.size();
    }

    public class CastCrewViewHolder extends RecyclerView.ViewHolder {
        private final ItemRowCastCrewBinding binding;

        public CastCrewViewHolder(@NonNull ItemRowCastCrewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Cast cast) {
            binding.tvName.setText(cast.getName());
            binding.tvRole.setText(cast.getCharacter());

            Glide.with(itemView)
                    .load((AppConstans.BASE_URL_W300) + cast.getProfilePath())
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
                    .apply(AppConstans.getOptionsAvatar())
                    .into(binding.ivPoster);
        }
        private void bind(Crew crew){
            binding.tvName.setText(crew.getName());
            binding.tvRole.setText(crew.getJob());

            Glide.with(itemView)
                    .load((AppConstans.BASE_URL_W300) + crew.getProfilePath())
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
                    .apply(AppConstans.getOptionsAvatar())
                    .into(binding.ivPoster);
        }
    }
}
