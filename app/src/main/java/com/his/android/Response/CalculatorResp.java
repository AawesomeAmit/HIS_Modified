package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ControlList;
import com.his.android.Model.Result;

import java.util.List;

public class CalculatorResp {
    @SerializedName("controlList")
    @Expose
    public List<ControlList> controlList = null;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public List<ControlList> getControlList() {
        return controlList;
    }

    public List<Result> getResult() {
        return result;
    }
}
