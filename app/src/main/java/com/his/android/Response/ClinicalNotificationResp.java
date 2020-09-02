package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ClinicalNotification;

import java.util.List;

public class ClinicalNotificationResp {
    @SerializedName("clinicalNotification")
    @Expose
    public List<ClinicalNotification> clinicalNotification = null;

    public List<ClinicalNotification> getClinicalNotification() {
        return clinicalNotification;
    }
}
