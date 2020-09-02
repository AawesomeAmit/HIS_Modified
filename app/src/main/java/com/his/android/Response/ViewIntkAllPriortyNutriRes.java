package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ViewIntkAllPriortyNutriList;

import java.util.List;

public class ViewIntkAllPriortyNutriRes {
   @SerializedName("responseCode")
   @Expose
   public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<ViewIntkAllPriortyNutriList> responseValue = null;
    @SerializedName("energyConsumed")
    @Expose
    public String energyConsumed;
    @SerializedName("energyTarget")
    @Expose
    public String energyTarget;
    @SerializedName("energyAchievedRDAPercentage")
    @Expose
    public Integer energyAchievedRDAPercentage;
    @SerializedName("energyColorCode")
    @Expose
    public String energyColorCode;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<ViewIntkAllPriortyNutriList> getResponseValue() {
        return responseValue;
    }

    public String getEnergyConsumed() {
        return energyConsumed;
    }

    public String getEnergyTarget() {
        return energyTarget;
    }

    public Integer getEnergyAchievedRDAPercentage() {
        return energyAchievedRDAPercentage;
    }

    public String getEnergyColorCode() {
        return energyColorCode;
    }
}
