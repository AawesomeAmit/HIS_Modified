package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AngioPlastyTable;

import java.util.List;

public class AngioplastyResp {
    @SerializedName("table")
    @Expose
    public List<AngioPlastyTable> table = null;

    public List<AngioPlastyTable> getTable() {
        return table;
    }
}
