package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetailDashboard {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("diagnosis")
    @Expose
    public String diagnosis;
    @SerializedName("currentWardID")
    @Expose
    public Integer currentWardID;
    @SerializedName("currentWardName")
    @Expose
    public String currentWardName;
    @SerializedName("correctWardID")
    @Expose
    public Integer correctWardID;
    @SerializedName("correctWardName")
    @Expose
    public String correctWardName;

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Integer getCurrentWardID() {
        return currentWardID;
    }

    public String getCurrentWardName() {
        return currentWardName;
    }

    public Integer getCorrectWardID() {
        return correctWardID;
    }

    public String getCorrectWardName() {
        return correctWardName;
    }

    public Integer getPmID() {
        return pmID;
    }
}
