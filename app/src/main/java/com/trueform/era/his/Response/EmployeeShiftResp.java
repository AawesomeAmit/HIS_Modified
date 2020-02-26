package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.EmployeeList;

import java.util.List;

public class EmployeeShiftResp {
    @SerializedName("employeeList")
    @Expose
    public List<EmployeeList> employeeList = null;

    public List<EmployeeList> getEmployeeList() {
        return employeeList;
    }
}
