package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.MedicosDepartmentList;

import java.util.List;

public class MedicosDepartmentRes {
    @SerializedName("medicosDepartmentList")
    @Expose
    public List<MedicosDepartmentList> medicosDepartmentList = null;

    public List<MedicosDepartmentList> getMedicosDepartmentList() {
        return medicosDepartmentList;
    }
}
