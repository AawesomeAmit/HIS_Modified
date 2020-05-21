package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlertInput {
    @SerializedName("inputCategoryId")
    @Expose
    public Integer inputCategoryId;
    @SerializedName("clinicalCategoryName")
    @Expose
    public String clinicalCategoryName;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("value")
    @Expose
    public Double value;
    @SerializedName("unitName")
    @Expose
    public String unitName;

    public Integer getInputCategoryId() {
        return inputCategoryId;
    }

    public String getClinicalCategoryName() {
        return clinicalCategoryName;
    }

    public String getReference() {
        return reference;
    }

    public Double getValue() {
        return value;
    }

    public String getUnitName() {
        return unitName;
    }
}
