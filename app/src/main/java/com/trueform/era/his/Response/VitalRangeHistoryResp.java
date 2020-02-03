package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VitalRangeHistoryResp {
    @SerializedName("vitalRangeHistory")
    @Expose
    public List<VitalRangeHistory> vitalRangeHistory = null;

    public List<VitalRangeHistory> getVitalRangeHistory() {
        return vitalRangeHistory;
    }
}
