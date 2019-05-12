package com.fizus.mobiledev.finmov.data.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cast")
public class Cast {
    @SerializedName("cast_id")
    @Expose
    @ColumnInfo(name = "cast_id")
    private long castId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    @ColumnInfo(name = "credit_id")
    private String creditId;
    @SerializedName("gender")
    @Expose
    private long gender;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private long order;
    @SerializedName("profile_path")
    @Expose
    @ColumnInfo(name = "profile_path")
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

    public void setCastId(long castId) {
        this.castId = castId;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
