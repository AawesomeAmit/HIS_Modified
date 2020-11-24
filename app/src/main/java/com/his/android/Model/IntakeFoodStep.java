package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeFoodStep {
    @SerializedName("dietID")
    @Expose
    public Integer dietID;
    @SerializedName("foodName")
    @Expose
    public String foodName;
    @SerializedName("foodQuantity")
    @Expose
    public String foodQuantity;
    @SerializedName("unit")
    @Expose
    public String unit;
    public String percent;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Integer getDietID() {
        return dietID;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public String getUnit() {
        return unit;
    }
}
