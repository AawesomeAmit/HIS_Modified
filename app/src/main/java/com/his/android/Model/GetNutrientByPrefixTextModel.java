package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNutrientByPrefixTextModel {
    @SerializedName("nutrientID")
    @Expose
    private Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public void setNutrientID(Integer nutrientID) {
        this.nutrientID = nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    @Override
    public String toString() {
        return nutrientName;
    }
}
