package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DoctorNameList;
import com.his.android.Model.EducationList;
import com.his.android.Model.StateMaster;
import com.his.android.Model.WardNameList;

import java.util.List;

public class PatientRegistrationListResp {
    @SerializedName("stateMaster")
    @Expose
    public List<StateMaster> stateMaster = null;
    @SerializedName("doctorNameList")
    @Expose
    public List<DoctorNameList> doctorNameList = null;
    @SerializedName("wardNameList")
    @Expose
    public List<WardNameList> wardNameList = null;
    @SerializedName("educationList")
    @Expose
    public List<EducationList> educationList = null;

    public List<StateMaster> getStateMaster() {
        return stateMaster;
    }

    public List<DoctorNameList> getDoctorNameList() {
        return doctorNameList;
    }

    public List<WardNameList> getWardNameList() {
        return wardNameList;
    }

    public List<EducationList> getEducationList() {
        return educationList;
    }
}
