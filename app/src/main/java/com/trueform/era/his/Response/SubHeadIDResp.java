package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.SubDept;

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
