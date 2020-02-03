package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProblemList {
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
    public Integer getId() {
        return id;
    }

    public String getProblemName() {
        return problemName;
    }
}
