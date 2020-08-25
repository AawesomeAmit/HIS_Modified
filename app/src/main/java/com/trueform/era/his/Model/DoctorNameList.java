package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorNameList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;

    @Override
    public String toString() {
        return doctorName;
    }
    public Integer getId() {
        return id;
    }

    public DoctorNameList(Integer id, String doctorName) {
        this.id = id;
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
