package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AdmittedPatientICU;
import com.his.android.Model.DischargePatient;

import java.util.List;

public class IcuPatientListResp {
    @SerializedName("admittedPatientICU")
    @Expose
    public List<AdmittedPatientICU> admittedPatientICU = null;
    @SerializedName("dischargePatient")
    @Expose
    public List<DischargePatient> dischargePatient = null;
    @SerializedName("toBeAdmittedPatientICU")
    @Expose
    public List<Object> toBeAdmittedPatientICU = null;

    public List<AdmittedPatientICU> getAdmittedPatientICU() {
        return admittedPatientICU;
    }

    public void setAdmittedPatientICU(List<AdmittedPatientICU> admittedPatientICU) {
        this.admittedPatientICU = admittedPatientICU;
    }

    public List<DischargePatient> getDischargePatient() {
        return dischargePatient;
    }

    public List<Object> getToBeAdmittedPatientICU() {
        return toBeAdmittedPatientICU;
    }
}
