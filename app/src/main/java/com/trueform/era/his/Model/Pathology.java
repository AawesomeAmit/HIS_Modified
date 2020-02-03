package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pathology {
    @SerializedName("testName")
    @Expose
    public String testName;
    @SerializedName("testID")
    @Expose
    public Integer testID;
    @SerializedName("subTestName")
    @Expose
    public String subTestName;
    @SerializedName("subTestID")
    @Expose
    public Integer subTestID;
    @SerializedName("labRegNo")
    @Expose
    public String labRegNo;
    @SerializedName("subTestValue")
    @Expose
    public String subTestValue;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("min")
    @Expose
    public Float min;
    @SerializedName("max")
    @Expose
    public Float max;
    @SerializedName("testRange")
    @Expose
    public String testRange;

    public String getTestName() {
        return testName;
    }

    public Integer getTestID() {
        return testID;
    }

    public String getSubTestName() {
        return subTestName;
    }

    public Integer getSubTestID() {
        return subTestID;
    }

    public String getLabRegNo() {
        return labRegNo;
    }

    public String getSubTestValue() {
        return subTestValue;
    }

    public String getUnit() {
        return unit;
    }

    public Float getMin() {
        return min;
    }

    public Float getMax() {
        return max;
    }

    public String getTestRange() {
        return testRange;
    }
}
