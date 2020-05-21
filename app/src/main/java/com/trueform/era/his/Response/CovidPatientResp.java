package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientList;

import java.util.List;

public class CovidPatientResp {
    @SerializedName("patientList")
    @Expose
    public List<PatientList> patientList = null;

    public List<PatientList> getPatientList() {
        return patientList;
    }
}
