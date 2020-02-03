package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.UserMedicationReport;

import java.util.List;

public class UserMedicationReportResp {
    @SerializedName("userMedicationReport")
    @Expose
    public List<UserMedicationReport> userMedicationReport = null;

    public List<UserMedicationReport> getUserMedicationReport() {
        return userMedicationReport;
    }
}
