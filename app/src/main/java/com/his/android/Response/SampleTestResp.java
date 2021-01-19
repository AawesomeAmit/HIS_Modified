package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SampleTestList;

import java.util.List;

public class SampleTestResp {
    @SerializedName("table")
    @Expose
    public List<SampleTestList> table = null;

    public List<SampleTestList> getTable() {
        return table;
    }
}
