package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ClinicalNotification;

import java.util.List;

public class ClinicalNotificationResp {
    @SerializedName("clinicalNotification")
    @Expose
    public List<ClinicalNotification> clinicalNotification = null;

    public List<ClinicalNotification> getClinicalNotification() {
        return clinicalNotification;
    }
}
