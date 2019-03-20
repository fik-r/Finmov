package com.fizus.mobiledev.finmov.data.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("cast_id")
    @Expose
    private long castId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("gender")
    @Expose
    private long gender;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private long order;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public long getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditId() {
        return creditId;
    }

    public long getGender() {
        return gender;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
