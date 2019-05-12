package com.fizus.mobiledev.finmov.utils.converter;

import com.fizus.mobiledev.finmov.data.local.Cast;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class CastTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Cast> stringToCastList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Cast>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someMovieListToString(List<Cast> someObjects) {
        return gson.toJson(someObjects);
    }
}
