package com.fizus.mobiledev.finmov.data.network.responses;

import com.fizus.mobiledev.finmov.data.local.Cast;
import com.fizus.mobiledev.finmov.data.local.Crew;
import com.fizus.mobiledev.finmov.utils.converter.CastTypeConverter;
import com.fizus.mobiledev.finmov.utils.converter.CrewTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "credits_response")
public class GetCreditsResponse {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("cast")
    @Expose
    @TypeConverters(CastTypeConverter.class)
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    @TypeConverters(CrewTypeConverter.class)
    private List<Crew> crew = null;

    public long getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
