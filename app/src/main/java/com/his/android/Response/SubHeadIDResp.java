package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubDept;

import java.util.List;

public class SubHeadIDResp {
    @SerializedName("subDept")
    @Expose
    public List<SubDept> subDept = null;

    public void setSubDept(List<SubDept> subDept) {
        this.subDept = subDept;
    }

    public List<SubDept> getSubDept() {
        return subDept;
    }
}
