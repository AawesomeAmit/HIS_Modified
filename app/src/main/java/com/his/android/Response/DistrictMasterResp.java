package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DistrictMaster;

import java.util.List;

public class DistrictMasterResp {
    @SerializedName("districtMaster")
    @Expose
    public List<DistrictMaster> districtMaster = null;

    public List<DistrictMaster> getDistrictMaster() {
        return districtMaster;
    }
}
