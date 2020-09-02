package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhysioPatientList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("guardian")
    @Expose
    public String guardian;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;

    public PhysioPatientList(Integer pid, String patientName, String age, String gender) {
        this.pid = pid;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return patientName+"("+pid.toString()+")";
    }

    public Integer getId() {
        return id;
    }

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getGuardian() {
        return guardian;
    }

    public String getAddress() {
        return address;
    }

    public String getIpNo() {
        return ipNo;
    }
}
