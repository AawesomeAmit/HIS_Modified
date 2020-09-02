package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.PatientDiagnosisIntakeListModel;
import com.his.android.Model.PatientDiagnosisOutputListModel;
import com.his.android.Model.PatientDiagnosisVitalListModel;

import java.util.List;

public class PatientDiagnosisResultResp {
    @SerializedName("vitalList")
    @Expose
    private List<PatientDiagnosisVitalListModel> vitalList = null;
    @SerializedName("intakeList")
    @Expose
    private List<PatientDiagnosisIntakeListModel> intakeList = null;
    @SerializedName("outputList")
    @Expose
    private List<PatientDiagnosisOutputListModel> outputList = null;

    public List<PatientDiagnosisVitalListModel> getVitalList() {
        return vitalList;
    }

    public List<PatientDiagnosisIntakeListModel> getIntakeList() {
        return intakeList;
    }

    public List<PatientDiagnosisOutputListModel> getOutputList() {
        return outputList;
    }


}
