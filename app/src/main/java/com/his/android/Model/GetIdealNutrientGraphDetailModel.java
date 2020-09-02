package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIdealNutrientGraphDetailModel {
    @SerializedName("sNo")
    @Expose
    public Integer sNo;
    @SerializedName("graphTime")
    @Expose
    public String graphTime;
    @SerializedName("nutrientValue")
    @Expose
    public Float nutrientValue;
    @SerializedName("intakeQuantity")
    @Expose
    public String intakeQuantity;

    public Integer getsNo() {
        return sNo;
    }

    public String getGraphTime() {
        return graphTime;
    }

    public Float getNutrientValue() {
        return nutrientValue;
    }

    public String getIntakeQuantity() {
        return intakeQuantity;
    }
}
