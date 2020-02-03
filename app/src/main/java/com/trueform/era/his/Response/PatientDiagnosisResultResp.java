package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientDiagnosisIntakeListModel;
import com.trueform.era.his.Model.PatientDiagnosisOutputListModel;
import com.trueform.era.his.Model.PatientDiagnosisVitalListModel;

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
