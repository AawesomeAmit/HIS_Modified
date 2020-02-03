package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResearchDiseaseNotificationResp {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("diagnosisName")
    @Expose
    public String diagnosisName;
    @SerializedName("patientType")
    @Expose
    public String patientType;
    @SerializedName("department")
    @Expose
    public String department;
    @SerializedName("consultantName")
    @Expose
    public String consultantName;

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getGender() {
        return gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public String getPatientType() {
        return patientType;
    }

    public String getDepartment() {
        return department;
    }

    public String getConsultantName() {
        return consultantName;
    }
}
