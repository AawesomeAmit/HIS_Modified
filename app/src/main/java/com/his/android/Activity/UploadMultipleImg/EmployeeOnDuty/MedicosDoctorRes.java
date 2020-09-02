package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicosDoctorRes {
    @SerializedName("medicosDoctorList")
    @Expose
    public List<MedicosDoctorList> medicosDoctorList = null;

    public List<MedicosDoctorList> getMedicosDoctorList() {
        return medicosDoctorList;
    }
}
