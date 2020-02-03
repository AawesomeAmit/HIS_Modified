package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubdeptList {
    @SerializedName("subids")
    @Expose
    public Integer subids;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;

    public SubdeptList(Integer subids, String subDepartmentName) {
        this.subids = subids;
        this.subDepartmentName = subDepartmentName;
    }

    @NonNull
    @Override
    public String toString() {
        return subDepartmentName;
    }

    public Integer getSubids() {
        return subids;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }
}
