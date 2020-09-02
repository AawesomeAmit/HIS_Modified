package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiseaseList {
    @SerializedName("userNotificationAssignID")
    @Expose
    public Integer userNotificationAssignID;
    @SerializedName("diseaseName")
    @Expose
    public String diseaseName;

    public Integer getUserNotificationAssignID() {
        return userNotificationAssignID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }
}
