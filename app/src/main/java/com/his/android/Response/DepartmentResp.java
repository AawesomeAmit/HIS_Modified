package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DepartmentList;

import java.util.List;

public class DepartmentResp {
    @SerializedName("departmentList")
    @Expose
    public List<DepartmentList> departmentList = null;

    public List<DepartmentList> getDepartmentList() {
        return departmentList;
    }
}
