package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AdmittedPatient;
import com.trueform.era.his.Model.ToBeAdmittedPatientIPD;
import com.trueform.era.his.Model.WardList;

import java.util.List;

public class IpdPatientListResp {
    @SerializedName("admittedPatient")
    @Expose
    public List<AdmittedPatient> admittedPatient = null;
    @SerializedName("toBeAdmittedPatientIPD")
    @Expose
    public List<ToBeAdmittedPatientIPD> toBeAdmittedPatientIPD = null;
    @SerializedName("wardList")
    @Expose
    public List<WardList> wardList = null;
    @SerializedName("dischargePatient")
    @Expose
    public List<Object> dischargePatient = null;

    public List<AdmittedPatient> getAdmittedPatient() {
        return admittedPatient;
    }

    public List<ToBeAdmittedPatientIPD> getToBeAdmittedPatientIPD() {
        return toBeAdmittedPatientIPD;
    }

    public List<WardList> getWardList() {
        return wardList;
    }

    public List<Object> getDischargePatient() {
        return dischargePatient;
    }
}
