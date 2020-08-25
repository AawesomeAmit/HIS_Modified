package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicosDepartmentList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;

    public Integer getId() {
        return id;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }
}
