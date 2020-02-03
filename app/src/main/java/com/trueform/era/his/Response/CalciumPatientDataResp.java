package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.CalciumPatientHourly;

import java.util.List;

public class CalciumPatientDataResp {
    @SerializedName("calciumPatientHourly")
    @Expose
    public List<CalciumPatientHourly> calciumPatientHourly = null;

    public List<CalciumPatientHourly> getCalciumPatientHourly() {
        return calciumPatientHourly;
    }
}
