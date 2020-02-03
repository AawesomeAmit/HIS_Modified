package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DiseasePatientList;

import java.util.List;

public class DiseasePatientListResp {
    @SerializedName("patientList")
    @Expose
    public List<DiseasePatientList> patientList = null;

    public List<DiseasePatientList> getPatientList() {
        return patientList;
    }
}
