package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AngioPlastyTable;

import java.util.List;

public class AngioplastyResp {
    @SerializedName("table")
    @Expose
    public List<AngioPlastyTable> table = null;

    public List<AngioPlastyTable> getTable() {
        return table;
    }
}
