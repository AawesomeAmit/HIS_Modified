package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsList {
    @SerializedName("doctorID")
    @Expose
    public Integer doctorID;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;

    @Override
    public String toString() {
        return doctorName;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public DoctorsList(Integer doctorID, String doctorName) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
    }
}
