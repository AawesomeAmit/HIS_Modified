package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NonMedicoEmployeeRes {
    @SerializedName("dtAttendance")
    @Expose
    public List<NonMedicoEmployeeList> dtAttendance = null;

    public List<NonMedicoEmployeeList> getDtAttendance() {
        return dtAttendance;
    }
}
