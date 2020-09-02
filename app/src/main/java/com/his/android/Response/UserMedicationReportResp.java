package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.UserMedicationReport;

import java.util.List;

public class UserMedicationReportResp {
    @SerializedName("userMedicationReport")
    @Expose
    public List<UserMedicationReport> userMedicationReport = null;

    public List<UserMedicationReport> getUserMedicationReport() {
        return userMedicationReport;
    }
}
