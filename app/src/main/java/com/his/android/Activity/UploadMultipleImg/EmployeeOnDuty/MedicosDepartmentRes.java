package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicosDepartmentRes {
    @SerializedName("medicosDepartmentList")
    @Expose
    public List<MedicosDepartmentList> medicosDepartmentList = null;

    public List<MedicosDepartmentList> getMedicosDepartmentList() {
        return medicosDepartmentList;
    }
}
