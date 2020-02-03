package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiseasePatientList {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("diseaseName")
    @Expose
    public String diseaseName;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
