package com.fizus.mobiledev.finmov.utils.converter;

import com.fizus.mobiledev.finmov.data.local.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class MovieTypeConverters {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Movie> stringToMovieList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someMovieListToString(List<Movie> someObjects) {
        return gson.toJson(someObjects);
    }
}
