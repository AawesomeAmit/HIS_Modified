package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeList {
    @SerializedName("empid")
    @Expose
    public Integer empid;
    @SerializedName("shiftdate")
    @Expose
    public String shiftdate;
    @SerializedName("mobileno")
    @Expose
    public String mobileno;
    @SerializedName("empName")
    @Expose
    public String empName;

    public Integer getEmpid() {
        return empid;
    }

    public String getShiftdate() {
        return shiftdate;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getEmpName() {
        return empName;
    }
}
