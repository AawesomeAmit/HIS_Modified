package com.trueform.era.his.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentList {
    @SerializedName("deptID")
    @Expose
    public Integer deptID;
    @SerializedName("deptName")
    @Expose
    public String deptName;
    @Override
    public String toString() {
        return deptName;
    }

    public DepartmentList(Integer deptID, String deptName) {
        this.deptID = deptID;
        this.deptName = deptName;
    }

    public Integer getDeptID() {
        return deptID;
    }

    public String getDeptName() {
        return deptName;
    }
}
