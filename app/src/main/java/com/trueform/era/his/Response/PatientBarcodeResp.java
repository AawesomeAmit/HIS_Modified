package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientInfoBarcode;

import java.util.List;

public class PatientBarcodeResp {
    @SerializedName("patientInfo")
    @Expose
    public List<PatientInfoBarcode> patientInfo = null;

    public List<PatientInfoBarcode> getPatientInfo() {
        return patientInfo;
    }
}
