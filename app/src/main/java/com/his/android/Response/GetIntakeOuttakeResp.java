package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.IntakeHistory;
import com.his.android.Model.OutputHistory;
import com.his.android.Model.PatientInfo;
import com.his.android.Model.SumTotalInOutput;

import java.util.List;

public class GetIntakeOuttakeResp {
    @SerializedName("patientInfo")
    @Expose
    public List<PatientInfo> patientInfo = null;
    @SerializedName("intakeHistory")
    @Expose
    public List<IntakeHistory> intakeHistory = null;
    @SerializedName("outputHistory")
    @Expose
    public List<OutputHistory> outputHistory = null;
    @SerializedName("sumTotalInOutput")
    @Expose
    public List<SumTotalInOutput> sumTotalInOutput = null;

    public List<PatientInfo> getPatientInfo() {
        return patientInfo;
    }

    public List<IntakeHistory> getIntakeHistory() {
        return intakeHistory;
    }

    public List<OutputHistory> getOutputHistory() {
        return outputHistory;
    }

    public List<SumTotalInOutput> getSumTotalInOutput() {
        return sumTotalInOutput;
    }
}
