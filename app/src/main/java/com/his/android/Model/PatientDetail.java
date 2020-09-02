package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetail {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pname")
    @Expose
    public String pname;
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
    @SerializedName("ipNo")
    @Expose
    public String ipNo;

    public Integer getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
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

    public PatientDetail(Integer pid, String pname, String sex, String gender, String age, String ageUnit) {
        this.pid = pid;
        this.pname = pname;
        this.sex = sex;
        this.gender = gender;
        this.age = age;
        this.ageUnit = ageUnit;
    }
}
