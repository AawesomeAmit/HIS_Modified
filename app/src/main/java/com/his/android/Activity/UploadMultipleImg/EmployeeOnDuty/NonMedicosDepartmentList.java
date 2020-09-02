package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonMedicosDepartmentList {
    @SerializedName("deptName")
    @Expose
    public String deptName;
    @SerializedName("deptID")
    @Expose
    public Integer deptID;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("id")
    @Expose
    public Integer id;

    public String getDeptName() {
        return deptName;
    }

    public Integer getDeptID() {
        return deptID;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public Integer getId() {
        return id;
    }
}
