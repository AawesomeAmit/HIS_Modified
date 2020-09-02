package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNutrientInBodyModel {
    @SerializedName("nutrientID")
    @Expose
    private Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;
    @SerializedName("nutrientRDA")
    @Expose
    private Double nutrientRDA;
    @SerializedName("nutrientValue")
    @Expose
    private Double nutrientValue;
    @SerializedName("valueDateTime")
    @Expose
    private String valueDateTime;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public Double getNutrientRDA() {
        return nutrientRDA;
    }

    public Double getNutrientValue() {
        return nutrientValue;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }
}
