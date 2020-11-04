package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientActivityDetail {
    @SerializedName("physicalActivityID")
    @Expose
    public Integer physicalActivityID;
    @SerializedName("physicalActivityName")
    @Expose
    public String physicalActivityName;
    @SerializedName("patientPhysicalActivityID")
    @Expose
    public Integer patientPhysicalActivityID;
    @SerializedName("activityStatus")
    @Expose
    public String activityStatus;
    @SerializedName("iconImage")
    @Expose
    public String iconImage;

    public Integer getPhysicalActivityID() {
        return physicalActivityID;
    }

    public String getPhysicalActivityName() {
        return physicalActivityName;
    }

    public Integer getPatientPhysicalActivityID() {
        return patientPhysicalActivityID;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public String getIconImage() {
        return iconImage;
    }
}
