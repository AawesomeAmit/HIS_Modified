package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table1 {
    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("nutrientQuantity")
    @Expose
    public Float nutrientQuantity;
    @SerializedName("intakeDateTime")
    @Expose
    public String intakeDateTime;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public Float getNutrientQuantity() {
        return nutrientQuantity;
    }

    public String getIntakeDateTime() {
        return intakeDateTime;
    }
}
