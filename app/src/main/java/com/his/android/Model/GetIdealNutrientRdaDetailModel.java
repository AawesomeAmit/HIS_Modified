package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIdealNutrientRdaDetailModel {
    @SerializedName("rda")
    @Expose
    public Integer rda;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("nutrientThalfMin")
    @Expose
    public String nutrientThalfMin;
    @SerializedName("nutrientPeakMin")
    @Expose
    public String nutrientPeakMin;
    @SerializedName("totalNoOfIntakes")
    @Expose
    public Integer totalNoOfIntakes;

    public Integer getRda() {
        return rda;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getNutrientThalfMin() {
        return nutrientThalfMin;
    }

    public String getNutrientPeakMin() {
        return nutrientPeakMin;
    }

    public Integer getTotalNoOfIntakes() {
        return totalNoOfIntakes;
    }
}
