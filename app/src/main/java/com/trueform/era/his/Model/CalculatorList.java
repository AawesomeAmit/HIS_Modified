package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculatorList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("calculatorTitle")
    @Expose
    public String calculatorTitle;
    @SerializedName("calculatorType")
    @Expose
    public Object calculatorType;
    @SerializedName("remark")
    @Expose
    public Object remark;
    @SerializedName("reference")
    @Expose
    public Object reference;
    @SerializedName("status")
    @Expose
    public Integer status;

    @Override
    public String toString() {
        return calculatorTitle;
    }

    public Integer getId() {
        return id;
    }

    public String getCalculatorTitle() {
        return calculatorTitle;
    }

    public Object getCalculatorType() {
        return calculatorType;
    }

    public Object getRemark() {
        return remark;
    }

    public Object getReference() {
        return reference;
    }

    public Integer getStatus() {
        return status;
    }
}
