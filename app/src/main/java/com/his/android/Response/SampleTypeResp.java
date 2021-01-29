package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SampleType;

import java.util.List;

public class SampleTypeResp {
    @SerializedName("table")
    @Expose
    public List<SampleType> table = null;

    public List<SampleType> getTable() {
        return table;
    }
}
