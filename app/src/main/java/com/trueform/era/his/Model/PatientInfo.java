package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientInfo {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pname")
    @Expose
    public String pname;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("sex")
    @Expose
    public String sex;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("phoneNo")
    @Expose
    public String phoneNo;
    @SerializedName("crNo1")
    @Expose
    public String crNo1;
    @SerializedName("consultant")
    @Expose
    public String consultant;

    public Integer getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getCrNo() {
        return crNo;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getSex() {
        return sex;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCrNo1() {
        return crNo1;
    }

    public String getConsultant() {
        return consultant;
    }
}
