package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NonMedicosDepartmentRes {
    @SerializedName("dept")
    @Expose
    public List<NonMedicosDepartmentList> dept = null;

    public List<NonMedicosDepartmentList> getDept() {
        return dept;
    }
}
