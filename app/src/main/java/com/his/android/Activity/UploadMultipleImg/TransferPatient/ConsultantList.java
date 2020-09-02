package com.his.android.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultantList {
    @SerializedName("userID")
    @Expose
    public Integer userID;
    @SerializedName("displayName")
    @Expose
    public String displayName;

    public Integer getUserID() {
        return userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
