package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodList {
    @SerializedName("dietID")
    @Expose
    public Integer dietID;
    @SerializedName("foodID")
    @Expose
    public Integer foodID;
    @SerializedName("foodName")
    @Expose
    public String foodName;
    @SerializedName("foodQuantity")
    @Expose
    public Float foodQuantity;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("dietTimeId")
    @Expose
    public Integer dietTimeId;
    @SerializedName("dietTiming")
    @Expose
    public String dietTiming;
    @SerializedName("dietType")
    @Expose
    public Integer dietType;

    public Integer getDietID() {
        return dietID;
    }

    public Integer getFoodID() {
        return foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public Float getFoodQuantity() {
        return foodQuantity;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getDietTimeId() {
        return dietTimeId;
    }

    public String getDietTiming() {
        return dietTiming;
    }

    public Integer getDietType() {
        return dietType;
    }
}
