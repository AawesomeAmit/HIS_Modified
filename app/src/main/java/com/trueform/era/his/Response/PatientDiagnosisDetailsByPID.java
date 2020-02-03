package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Diagnosi;
import com.trueform.era.his.Model.Patient;
import com.trueform.era.his.Model.PatientHistory;

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
