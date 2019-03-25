package com.fizus.mobiledev.finmov.utils;

import com.fizus.mobiledev.finmov.data.local.Genre;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class GenreTypeConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Genre> stringToGenres(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Genre>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String genresToString(List<Genre> genres) {
        return gson.toJson(genres);
    }
}
