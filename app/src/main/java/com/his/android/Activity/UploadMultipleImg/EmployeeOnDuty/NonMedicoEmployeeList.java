package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonMedicoEmployeeList {
    @SerializedName("empID")
    @Expose
    public String empID;
    @SerializedName("empName")
    @Expose
    public String empName;
    @SerializedName("empDesig")
    @Expose
    public String empDesig;
    @SerializedName("empDept")
    @Expose
    public String empDept;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("covidLocation")
    @Expose
    public String covidLocation;

    public String getEmpID() {
        return empID;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpDesig() {
        return empDesig;
    }

    public String getEmpDept() {
        return empDept;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getCovidLocation() {
        return covidLocation;
    }
}
