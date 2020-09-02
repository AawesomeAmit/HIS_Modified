package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientInfo;
import com.his.android.Model.ProgressList;
import com.his.android.Model.VitalChart;
import java.util.List;

public class ViewProgressResp {
    @SerializedName("patientInfo")
    @Expose
    public List<PatientInfo> patientInfo = null;
    @SerializedName("progressList")
    @Expose
    public List<ProgressList> progressList = null;
    @SerializedName("lastVitals")
    @Expose
    public List<VitalChart> lastVitals = null;

    public List<PatientInfo> getPatientInfo() {
        return patientInfo;
    }

    public List<VitalChart> getLastVitals() {
        return lastVitals;
    }

    public List<ProgressList> getProgressList() {
        return progressList;
    }
}
