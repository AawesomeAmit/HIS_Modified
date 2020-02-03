package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRequiredSupplementByFoodTimeId {
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("nutrientValue")
    @Expose
    public String nutrientValue;
    @SerializedName("rda")
    @Expose
    public String rda;
    @SerializedName("requiredQuantity")
    @Expose
    public String requiredQuantity;
    @SerializedName("supplementName")
    @Expose
    public String supplementName;
    @SerializedName("supplementDose")
    @Expose
    public String supplementDose;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getNutrientValue() {
        return nutrientValue;
    }

    public String getRda() {
        return rda;
    }

    public String getRequiredQuantity() {
        return requiredQuantity;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public String getSupplementDose() {
        return supplementDose;
    }
}
