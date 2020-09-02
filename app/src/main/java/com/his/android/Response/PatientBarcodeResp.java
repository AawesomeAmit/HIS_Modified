package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientInfoBarcode;

import java.util.List;

public class PatientBarcodeResp {
    @SerializedName("patientInfo")
    @Expose
    public List<PatientInfoBarcode> patientInfo = null;

    public List<PatientInfoBarcode> getPatientInfo() {
        return patientInfo;
    }
}
