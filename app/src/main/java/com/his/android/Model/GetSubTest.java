package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSubTest {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subTestName")
    @Expose
    public String subTestName;
    @SerializedName("result")
    @Expose
    public String result;
    @SerializedName("resultRemark")
    @Expose
    public String resultRemark;
    @SerializedName("unitname")
    @Expose
    public String unitname;
    @SerializedName("isNormalResult")
    @Expose
    public Boolean isNormalResult;

    public Integer getId() {
        return id;
    }

    public String getSubTestName() {
        return subTestName;
    }

    public String getResult() {
        return result;
    }

    public String getResultRemark() {
        return resultRemark;
    }

    public String getUnitname() {
        return unitname;
    }

    public Boolean getNormalResult() {
        return isNormalResult;
    }
}
