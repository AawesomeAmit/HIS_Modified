package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientRegistration;

import java.util.List;

public class PatientRegistrationResp {
    @SerializedName("patientRegistration")
    @Expose
    public List<PatientRegistration> patientRegistration = null;

    public List<PatientRegistration> getPatientRegistration() {
        return patientRegistration;
    }
}
