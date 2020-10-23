package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.LifeSupportMasterList;

import java.util.List;

public class LifeSupportMasterResp {
    @SerializedName("lifeSupportMasterList")
    @Expose
    public List<LifeSupportMasterList> lifeSupportMasterList = null;

    public List<LifeSupportMasterList> getLifeSupportMasterList() {
        return lifeSupportMasterList;
    }
}
