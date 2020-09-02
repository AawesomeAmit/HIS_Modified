package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Diagnosi;
import com.his.android.Model.Patient;
import com.his.android.Model.PatientHistory;

import java.util.List;

public class PatientDiagnosisDetailsByPID {
    @SerializedName("patient")
    @Expose
    public List<Patient> patient = null;
    @SerializedName("diagnosis")
    @Expose
    public List<Diagnosi> diagnosis = null;
    @SerializedName("patientHistory")
    @Expose
    public List<PatientHistory> patientHistory = null;
    @SerializedName("prescription")
    @Expose
    public List<Object> prescription = null;
    @SerializedName("abnormalResult")
    @Expose
    public List<Object> abnormalResult = null;

    public List<Patient> getPatient() {
        return patient;
    }

    public List<Diagnosi> getDiagnosis() {
        return diagnosis;
    }

    public List<PatientHistory> getPatientHistory() {
        return patientHistory;
    }

    public List<Object> getPrescription() {
        return prescription;
    }

    public List<Object> getAbnormalResult() {
        return abnormalResult;
    }
}
