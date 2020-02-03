package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewIntkAllPriortyNutriList {
  /*  @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("consumed")
    @Expose
    public String consumed;
    @SerializedName("target")
    @Expose
    public String target;
    @SerializedName("achievedRDAPercentage")
    @Expose
    public Integer achievedRDAPercentage;
    @SerializedName("colorCode")
    @Expose
    public String colorCode;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getConsumed() {
        return consumed;
    }

    public String getTarget() {
        return target;
    }

    public Integer getAchievedRDAPercentage() {
        return achievedRDAPercentage;
    }

    public String getColorCode() {
        return colorCode;
    }*/
  @SerializedName("nutrientName")
  @Expose
  public String nutrientName;
    @SerializedName("consumed")
    @Expose
    public String consumed;
    @SerializedName("consumedNormal")
    @Expose
    public String consumedNormal;
    @SerializedName("consumedExceed")
    @Expose
    public String consumedExceed;
    @SerializedName("target")
    @Expose
    public String target;
    @SerializedName("achievedRDAPercentage")
    @Expose
    public Integer achievedRDAPercentage;
    @SerializedName("colorCode")
    @Expose
    public String colorCode;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getConsumed() {
        return consumed;
    }

    public String getConsumedNormal() {
        return consumedNormal;
    }

    public String getConsumedExceed() {
        return consumedExceed;
    }

    public String getTarget() {
        return target;
    }

    public Integer getAchievedRDAPercentage() {
        return achievedRDAPercentage;
    }

    public String getColorCode() {
        return colorCode;
    }
}
