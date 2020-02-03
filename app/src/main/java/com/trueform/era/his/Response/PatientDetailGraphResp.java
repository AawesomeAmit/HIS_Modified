package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DetailList;
import com.trueform.era.his.Model.IntakeOutputList;
import com.trueform.era.his.Model.VitalListNutrient;

import java.util.List;

public class PatientDetailGraphResp {
    @SerializedName("detailList")
    @Expose
    public List<DetailList> detailList = null;
    @SerializedName("drugList")
    @Expose
    public List<Object> drugList = null;
    @SerializedName("vitalList")
    @Expose
    public List<VitalListNutrient> vitalList = null;
    @SerializedName("intakeOutputList")
    @Expose
    public List<IntakeOutputList> intakeOutputList = null;

    public List<DetailList> getDetailList() {
        return detailList;
    }

    public List<Object> getDrugList() {
        return drugList;
    }

    public List<VitalListNutrient> getVitalList() {
        return vitalList;
    }

    public List<IntakeOutputList> getIntakeOutputList() {
        return intakeOutputList;
    }
}
