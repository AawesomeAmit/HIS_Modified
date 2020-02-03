package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Crno;
import com.trueform.era.his.Model.PatientDetail;
import com.trueform.era.his.Model.Revisit;

import java.util.List;

public class CheckPidResp {
    @SerializedName("crno")
    @Expose
    public List<Crno> crno = null;
    @SerializedName("visit")
    @Expose
    public List<Object> visit = null;
    @SerializedName("revisit")
    @Expose
    public List<Revisit> revisit = null;
    @SerializedName("ipNo")
    @Expose
    public List<Object> ipNo = null;
    @SerializedName("patientDetails")
    @Expose
    public List<PatientDetail> patientDetails = null;

    public List<Crno> getCrno() {
        return crno;
    }

    public List<Object> getVisit() {
        return visit;
    }

    public List<Revisit> getRevisit() {
        return revisit;
    }

    public List<Object> getIpNo() {
        return ipNo;
    }

    public List<PatientDetail> getPatientDetails() {
        return patientDetails;
    }
}
