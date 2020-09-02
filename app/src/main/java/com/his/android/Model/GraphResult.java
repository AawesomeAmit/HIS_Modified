package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphResult {
    @SerializedName("subTestID")
    @Expose
    public Integer subTestID;
    @SerializedName("subTestName")
    @Expose
    public String subTestName;
    @SerializedName("subTestValue")
    @Expose
    public String subTestValue;
    @SerializedName("billNo")
    @Expose
    public String billNo;
    @SerializedName("min")
    @Expose
    public Float min;
    @SerializedName("max")
    @Expose
    public Float max;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("billDate")
    @Expose
    public String billDate;

    public Integer getSubTestID() {
        return subTestID;
    }

    public String getSubTestName() {
        return subTestName;
    }

    public String getSubTestValue() {
        return subTestValue;
    }

    public String getBillNo() {
        return billNo;
    }

    public Float getMin() {
        return min;
    }

    public Float getMax() {
        return max;
    }

    public String getUnit() {
        return unit;
    }

    public String getBillDate() {
        return billDate;
    }
}
