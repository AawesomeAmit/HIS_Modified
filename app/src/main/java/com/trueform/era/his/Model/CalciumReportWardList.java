package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalciumReportWardList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("wardName")
    @Expose
    public String wardName;

    @androidx.annotation.NonNull
    @Override
    public String toString() {
        return wardName;
    }

    public CalciumReportWardList(Integer id, String wardName) {
        this.id = id;
        this.wardName = wardName;
    }

    public Integer getId() {
        return id;
    }

    public String getWardName() {
        return wardName;
    }
}
