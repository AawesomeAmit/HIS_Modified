package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SubdeptList;

import java.util.List;

public class SubDeptCalReportResp {
    @SerializedName("subdeptList")
    @Expose
    public List<SubdeptList> subdeptList = null;

    public List<SubdeptList> getSubdeptList() {
        return subdeptList;
    }
}
