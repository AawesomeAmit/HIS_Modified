package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParameterScore {
    @SerializedName("score")
    @Expose
    public Object score;

    public Object getScore() {
        return score;
    }
}
