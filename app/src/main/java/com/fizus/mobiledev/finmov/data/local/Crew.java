package com.fizus.mobiledev.finmov.data.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew {
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("gender")
    @Expose
    private long gender;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
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
}
