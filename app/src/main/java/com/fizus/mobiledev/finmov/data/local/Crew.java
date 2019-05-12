package com.fizus.mobiledev.finmov.data.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crew")
public class Crew {
    @SerializedName("credit_id")
    @Expose
    @ColumnInfo(name = "credit_id")
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("gender")
    @Expose
    private long gender;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    @ColumnInfo(name = "profile_path")
    private String profilePath;

    public String getCreditId() {
        return creditId;
    }

    public String getDepartment() {
        return department;
    }

    public long getGender() {
        return gender;
    }

    public long getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
