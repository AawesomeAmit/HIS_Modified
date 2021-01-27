package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SampleSave;

import java.util.List;

public class SampleSaveResp {
    @SerializedName("table")
    @Expose
    public List<SampleSave> table = null;

    public List<SampleSave> getTable() {
        return table;
    }
}
