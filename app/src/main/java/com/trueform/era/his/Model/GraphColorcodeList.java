package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphColorcodeList {
    @SerializedName("achievedPercentage")
    @Expose
    public String achievedPercentage;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("colorCode")
    @Expose
    public String colorCode;

    public String getAchievedPercentage() {
        return achievedPercentage;
    }

    public String getColor() {
        return color;
    }

    public String getColorCode() {
        return colorCode;
    }
}
