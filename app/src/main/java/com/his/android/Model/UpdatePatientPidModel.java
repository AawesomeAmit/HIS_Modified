package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePatientPidModel {
    @SerializedName("statusMsg")
    @Expose
    public String statusMsg;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;

    public String getStatusMsg() {
        return statusMsg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
