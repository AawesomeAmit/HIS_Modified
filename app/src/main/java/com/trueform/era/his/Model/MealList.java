package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MealList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("foodName")
    @Expose
    public String foodName;

    @Override
    public String toString() {
        return foodName;
    }

    public Integer getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }
}
