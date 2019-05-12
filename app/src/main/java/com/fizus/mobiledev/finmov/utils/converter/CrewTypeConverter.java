package com.fizus.mobiledev.finmov.utils.converter;

import com.fizus.mobiledev.finmov.data.local.Crew;
import com.fizus.mobiledev.finmov.data.local.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class CrewTypeConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Crew> stringToCrewList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Crew>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someCrewListToString(List<Crew> someObjects) {
        return gson.toJson(someObjects);
    }
}
