package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.CasualityAreaList;

import java.util.List;

public class CasualityAreaRes {
    @SerializedName("casualityAreaList")
    @Expose
    public List<CasualityAreaList> casualityAreaList = null;

    public List<CasualityAreaList> getCasualityAreaList() {
        return casualityAreaList;
    }
}
