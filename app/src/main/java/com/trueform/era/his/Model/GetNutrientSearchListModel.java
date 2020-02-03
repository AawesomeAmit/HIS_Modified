package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNutrientSearchListModel {

    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    @Override
    public String toString() {
        return nutrientName;
    }
}
