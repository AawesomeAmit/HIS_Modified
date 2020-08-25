package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorOPDSchedule {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("roomID")
    @Expose
    public Integer roomID;
    @SerializedName("roomNo")
    @Expose
    public Integer roomNo;
    @SerializedName("subDepartmentID")
    @Expose
    public Integer subDepartmentID;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;
    @SerializedName("doctorID")
    @Expose
    public Integer doctorID;
    @SerializedName("dayName")
    @Expose
    public String dayName;
    @SerializedName("dayID")
    @Expose
    public Integer dayID;
    @SerializedName("designationID")
    @Expose
    public Integer designationID;
    @SerializedName("designationName")
    @Expose
    public String designationName;

    public DoctorOPDSchedule(Integer doctorID, String doctorName) {
        this.doctorName = doctorName;
        this.doctorID = doctorID;
    }

    @Override
    public String toString() {
        return doctorName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public Integer getSubDepartmentID() {
        return subDepartmentID;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public String getDayName() {
        return dayName;
    }

    public Integer getDayID() {
        return dayID;
    }

    public Integer getDesignationID() {
        return designationID;
    }

    public String getDesignationName() {
        return designationName;
    }
}
