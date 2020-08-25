package com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Activity.UploadMultipleImg.EmployeeOnDuty.MedicosDoctorList;

import java.util.List;

public class MedicosDoctorRes {
    @SerializedName("medicosDoctorList")
    @Expose
    public List<MedicosDoctorList> medicosDoctorList = null;

    public List<MedicosDoctorList> getMedicosDoctorList() {
        return medicosDoctorList;
    }
}
