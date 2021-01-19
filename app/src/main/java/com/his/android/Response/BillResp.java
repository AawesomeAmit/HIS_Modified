package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.BillList;

import java.util.List;

public class BillResp {
    @SerializedName("table")
    @Expose
    public List<BillList> table = null;

    public List<BillList> getTable() {
        return table;
    }

    public void setTable(List<BillList> table) {
        this.table = table;
    }
}
