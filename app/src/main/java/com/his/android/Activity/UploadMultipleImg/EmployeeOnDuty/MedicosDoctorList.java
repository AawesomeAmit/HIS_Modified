package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicosDoctorList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subDepartmentID")
    @Expose
    public Integer subDepartmentID;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("designation")
    @Expose
    public String designation;
    @SerializedName("casualityArea")
    @Expose
    public String casualityArea;
    @SerializedName("dateFrom")
    @Expose
    public String dateFrom;
    @SerializedName("dateTo")
    @Expose
    public String dateTo;

    public Integer getId() {
        return id;
    }

    public Integer getSubDepartmentID() {
        return subDepartmentID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getCasualityArea() {
        return casualityArea;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
}
