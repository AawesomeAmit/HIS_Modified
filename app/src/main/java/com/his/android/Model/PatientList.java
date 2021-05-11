package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientList {
    @SerializedName("appointmentId")
    @Expose
    public Integer appointmentId;
    @SerializedName("memberID")
    @Expose
    public Integer memberID;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("regDate")
    @Expose
    public String regDate;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("problem")
    @Expose
    public String problem;
    @SerializedName("patientDetails")
    @Expose
    public String patientDetails;

    @Override
    public String toString() {
        return name +"("+pid+")";
    }

    public PatientList(Integer appointmentId, Integer memberID, Integer pid, String name, String gender, String age, String mobileNo) {
        this.appointmentId = appointmentId;
        this.memberID = memberID;
        this.pid = pid;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.mobileNo = mobileNo;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public Integer getPid() {
        return pid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getAge() {
        return age;
    }

    public String getProblem() {
        return problem;
    }

    public String getPatientDetails() {
        return patientDetails;
    }
}
