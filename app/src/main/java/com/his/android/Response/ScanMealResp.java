package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ScanMeal;

import java.util.List;

public class ScanMealResp {
    @SerializedName("table")
    @Expose
    public List<ScanMeal> table = null;

    public List<ScanMeal> getTable() {
        return table;
    }
}
