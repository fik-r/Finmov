package com.fizus.mobiledev.finmov.utils;

import com.bumptech.glide.request.RequestOptions;
import com.fizus.mobiledev.finmov.R;

public class AppConstans {
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_MOVIE_TYPE = "movie_type";
    public static final String BASE_URL_W300 = "https://image.tmdb.org/t/p/w300";
    public static final String BASE_URL_W500 = "https://image.tmdb.org/t/p/w500";

    public static RequestOptions getOptionsAvatar(){
        return new RequestOptions()
                .fitCenter()
                .error(R.drawable.placeholder_avatar);
    }

    public static RequestOptions getOptionsDefault(){
        return new RequestOptions()
                .fitCenter()
                .error(R.drawable.placeholder);
    }
}
