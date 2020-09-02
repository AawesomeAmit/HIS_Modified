package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DiseasePatientList;

import java.util.List;

public class DiseasePatientListResp {
    @SerializedName("patientList")
    @Expose
    public List<DiseasePatientList> patientList = null;

    public List<DiseasePatientList> getPatientList() {
        return patientList;
    }
}
