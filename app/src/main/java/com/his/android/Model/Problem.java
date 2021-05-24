package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Problem {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("problemName")
    @Expose
    public String problemName;

    @Override
    public String toString() {
        return problemName;
    }

    public Problem(Integer id, String problemName) {
        this.id = id;
        this.problemName = problemName;
    }

    public Integer getId() {
        return id;
    }

    public String getProblemName() {
        return problemName;
    }
}
