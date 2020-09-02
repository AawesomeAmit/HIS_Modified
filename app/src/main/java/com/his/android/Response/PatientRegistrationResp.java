package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientRegistration;

import java.util.List;

public class PatientRegistrationResp {
    @SerializedName("patientRegistration")
    @Expose
    public List<PatientRegistration> patientRegistration = null;

    public List<PatientRegistration> getPatientRegistration() {
        return patientRegistration;
    }
}
