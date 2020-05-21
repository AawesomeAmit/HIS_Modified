package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Diagnosi;
import com.trueform.era.his.Model.Dietary;
import com.trueform.era.his.Model.Patient;
import com.trueform.era.his.Model.PatientHistory;
import com.trueform.era.his.Model.Prescription;
import java.util.List;

public class PrescribedMedResp {
    @SerializedName("patient")
    @Expose
    public List<Patient> patient = null;
    @SerializedName("dietary")
    @Expose
    public List<Dietary> dietaryList = null;
    @SerializedName("diagnosis")
    @Expose
    public List<Diagnosi> diagnosis = null;
    @SerializedName("patientHistory")
    @Expose
    public List<PatientHistory> patientHistory = null;
    @SerializedName("prescription")
    @Expose
    public List<Prescription> prescription = null;

    public void setPrescription(List<Prescription> prescription) {
        this.prescription = prescription;
    }

    public PrescribedMedResp() {
    }

    public List<Dietary> getDietaryList() {
        return dietaryList;
    }

    public List<Patient> getPatient() {
        return patient;
    }

    public List<Diagnosi> getDiagnosis() {
        return diagnosis;
    }

    public List<PatientHistory> getPatientHistory() {
        return patientHistory;
    }

    public List<Prescription> getPrescription() {
        return prescription;
    }
}
