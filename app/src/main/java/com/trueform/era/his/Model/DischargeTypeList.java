package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DischargeTypeList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dischargeType")
    @Expose
    public String dischargeType;

    public DischargeTypeList(Integer id, String dischargeType) {
        this.id = id;
        this.dischargeType = dischargeType;
    }

    @NonNull
    @Override
    public String toString() {
        return dischargeType;
    }

    public Integer getId() {
        return id;
    }

    public String getDischargeType() {
        return dischargeType;
    }
}
