package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RangeHistoryResp {
    @SerializedName("rangeHistory")
    @Expose
    public List<RangeHistory> rangeHistory = null;

    public List<RangeHistory> getRangeHistory() {
        return rangeHistory;
    }
}
