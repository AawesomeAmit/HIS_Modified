package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientInfo;
import com.his.android.Model.PrescriptionList;

import java.util.List;

public class GetPrescriptionResponse {
    @SerializedName("patientInfo")
    @Expose
    public List<PatientInfo> patientInfo = null;
    @SerializedName("prescriptionList")
    @Expose
    public List<PrescriptionList> prescriptionList = null;

    public List<PatientInfo> getPatientInfo() {
        return patientInfo;
    }

    public List<PrescriptionList> getPrescriptionList() {
        return prescriptionList;
    }
}
