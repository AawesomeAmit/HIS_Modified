package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DoctorNameList;
import com.trueform.era.his.Model.EducationList;
import com.trueform.era.his.Model.StateMaster;
import com.trueform.era.his.Model.WardNameList;

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
