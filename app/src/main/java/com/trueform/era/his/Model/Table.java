package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {
    @SerializedName("peak")
    @Expose
    public Float peak;
    @SerializedName("tHalf")
    @Expose
    public Float tHalf;
    @SerializedName("nutrient")
    @Expose
    public String nutrient;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("rda")
    @Expose
    public Float rda;

    public Float getPeak() {
        return peak;
    }

    public Float gettHalf() {
        return tHalf;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getUnitName() {
        return unitName;
    }

    public Float getRda() {
        return rda;
    }
}
