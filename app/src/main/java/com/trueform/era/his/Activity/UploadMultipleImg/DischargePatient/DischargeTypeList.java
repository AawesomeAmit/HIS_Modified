package com.trueform.era.his.Activity.UploadMultipleImg.DischargePatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DischargeTypeList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dischargeType")
    @Expose
    public String dischargeType;

    public Integer getId() {
        return id;
    }

    public String getDischargeType() {
        return dischargeType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDischargeType(String dischargeType) {
        this.dischargeType = dischargeType;
    }

    @Override
    public String toString() {
        return dischargeType;
    }
}
