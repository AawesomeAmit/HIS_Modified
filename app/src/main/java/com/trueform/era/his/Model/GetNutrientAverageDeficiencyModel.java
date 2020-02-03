package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNutrientAverageDeficiencyModel {

    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("nutrientAvg")
    @Expose
    public Float nutrientAvg;
    @SerializedName("rda")
    @Expose
    public Float rda;
    @SerializedName("achievedPercentage")
    @Expose
    public Float achievedPercentage;;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public Float getNutrientAvg() {
        return nutrientAvg;
    }

    public Float getRda() {
        return rda;
    }

    public Float getAchievedPercentage() {
        return achievedPercentage;
    }
}
