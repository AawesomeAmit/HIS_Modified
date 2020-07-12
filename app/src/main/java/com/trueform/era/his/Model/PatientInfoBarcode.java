package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientInfoBarcode {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("ageGender")
    @Expose
    public String ageGender;
    @SerializedName("admitSubDepartmentID")
    @Expose
    public Integer admitSubDepartmentID;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("consultantName")
    @Expose
    public String consultantName;
    @SerializedName("headId")
    @Expose
    public Integer headId;

    public Integer getPid() {
        return pid;
    }

    public Integer getPmID() {
        return pmID;
    }

    public String getCrNo() {
        return crNo;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getGender() {
        return gender;
    }

    public String getAgeGender() {
        return ageGender;
    }

    public Integer getAdmitSubDepartmentID() {
        return admitSubDepartmentID;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Integer getHeadId() {
        return headId;
    }
}
