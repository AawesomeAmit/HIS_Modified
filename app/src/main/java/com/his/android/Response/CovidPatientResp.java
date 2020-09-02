package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientList;

import java.util.List;

public class CovidPatientResp {
    @SerializedName("patientList")
    @Expose
    public List<PatientList> patientList = null;

    public List<PatientList> getPatientList() {
        return patientList;
    }
}
