package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dietary {
    @SerializedName("recommendedDiet")
    @Expose
    public String recommendedDiet;
    @SerializedName("avoidedDiet")
    @Expose
    public String avoidedDiet;
    @SerializedName("otherDiet")
    @Expose
    public String otherDiet;
    @SerializedName("advice")
    @Expose
    public String advice;

    public String getRecommendedDiet() {
        return recommendedDiet;
    }

    public String getAvoidedDiet() {
        return avoidedDiet;
    }

    public String getOtherDiet() {
        return otherDiet;
    }

    public String getAdvice() {
        return advice;
    }
}
