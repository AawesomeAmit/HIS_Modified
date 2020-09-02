package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubDept {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("bgColor")
    @Expose
    public String bgColor;
    @SerializedName("headID")
    @Expose
    public Integer headID;

    public SubDept(Integer id, String subDepartmentName) {
        this.id = id;
        this.subDepartmentName = subDepartmentName;
    }

    public Integer getId() {
        return id;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getBgColor() {
        return bgColor;
    }

    public Integer getHeadID() {
        return headID;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubDepartmentName(String subDepartmentName) {
        this.subDepartmentName = subDepartmentName;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setHeadID(Integer headID) {
        this.headID = headID;
    }
}
