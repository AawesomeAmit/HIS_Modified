package com.his.android.Activity.UploadMultipleImg.EmployeeOnDuty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CasualityAreaList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("casualityArea")
    @Expose
    public String casualityArea;

    public Integer getId() {
        return id;
    }

    public String getCasualityArea() {
        return casualityArea;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCasualityArea(String casualityArea) {
        this.casualityArea = casualityArea;
    }

    @Override
    public String toString() {
        return casualityArea;
    }
}
