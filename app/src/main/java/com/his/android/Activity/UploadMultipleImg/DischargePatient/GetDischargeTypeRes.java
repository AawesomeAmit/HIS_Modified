package com.his.android.Activity.UploadMultipleImg.DischargePatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDischargeTypeRes {
    @SerializedName("dischargeType")
    @Expose
    public List<DischargeTypeList> dischargeType = null;

    public List<DischargeTypeList> getDischargeType() {
        return dischargeType;
    }
}
