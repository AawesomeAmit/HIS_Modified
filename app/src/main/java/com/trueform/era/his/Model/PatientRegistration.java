package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientRegistration {
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("guardianName")
    @Expose
    public String guardianName;
    @SerializedName("subDepartmentName")
    @Expose
    public Object subDepartmentName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("roomNo")
    @Expose
    public Integer roomNo;
    @SerializedName("guardianMobileNo")
    @Expose
    public String guardianMobileNo;
    @SerializedName("registrationDate")
    @Expose
    public String registrationDate;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("queueNo")
    @Expose
    public Integer queueNo;
    @SerializedName("visitRevisitStatus")
    @Expose
    public String visitRevisitStatus;
    @SerializedName("vitalDetails")
    @Expose
    public String vitalDetails;

    public String getCrNo() {
        return crNo;
    }

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDob() {
        return dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getStateName() {
        return stateName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public Object getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getAddress() {
        return address;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public String getGuardianMobileNo() {
        return guardianMobileNo;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getQueueNo() {
        return queueNo;
    }

    public String getVisitRevisitStatus() {
        return visitRevisitStatus;
    }

    public String getVitalDetails() {
        return vitalDetails;
    }
}
