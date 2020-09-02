package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetailAngio {
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("billNo")
    @Expose
    public String billNo;
    @SerializedName("billDate")
    @Expose
    public String billDate;
    @SerializedName("pid")
    @Expose
    public Integer pid;
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
    @SerializedName("genderName")
    @Expose
    public String genderName;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("isOpd")
    @Expose
    public String isOpd;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;

    public Integer getPmID() {
        return pmID;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public Integer getPid() {
        return pid;
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

    public String getGenderName() {
        return genderName;
    }

    public String getDob() {
        return dob;
    }

    public String getAge() {
        return age;
    }

    public String getIsOpd() {
        return isOpd;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
