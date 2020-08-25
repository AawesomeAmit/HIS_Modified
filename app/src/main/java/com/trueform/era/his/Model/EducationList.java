package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("educationName")
    @Expose
    public String educationName;

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return educationName;
    }

    public EducationList(Integer id, String educationName) {
        this.id = id;
        this.educationName = educationName;
    }

    public String getEducationName() {
        return educationName;
    }
}
