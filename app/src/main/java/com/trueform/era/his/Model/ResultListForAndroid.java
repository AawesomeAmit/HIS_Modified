package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultListForAndroid {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("userLoginId")
    @Expose
    public Object userLoginId;
    @SerializedName("memberId")
    @Expose
    public Object memberId;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("admitDate")
    @Expose
    public String admitDate;
    @SerializedName("dischargeDate")
    @Expose
    public String dischargeDate;
    @SerializedName("wardName")
    @Expose
    public String wardName;
    @SerializedName("diagnosis")
    @Expose
    public String diagnosis;
    @SerializedName("dynamicDate")
    @Expose
    public List<DynamicDate> dynamicDate;

    public Integer getPid() {
        return pid;
    }

    public Integer getPmID() {
        return pmID;
    }

    public String getIpNo() {
        return ipNo;
    }

    public Object getUserLoginId() {
        return userLoginId;
    }

    public Object getMemberId() {
        return memberId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public String getWardName() {
        return wardName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public List<DynamicDate> getDynamicDate() {
        return dynamicDate;
    }
}
