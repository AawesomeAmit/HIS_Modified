package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ControlList;
import com.trueform.era.his.Model.Result;

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
