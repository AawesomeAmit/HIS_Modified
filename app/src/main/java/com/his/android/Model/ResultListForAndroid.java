package com.his.android.Model;

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

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setPmID(Integer pmID) {
        this.pmID = pmID;
    }

    public void setIpNo(String ipNo) {
        this.ipNo = ipNo;
    }

    public void setUserLoginId(Object userLoginId) {
        this.userLoginId = userLoginId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setDynamicDate(List<DynamicDate> dynamicDate) {
        this.dynamicDate = dynamicDate;
    }


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
