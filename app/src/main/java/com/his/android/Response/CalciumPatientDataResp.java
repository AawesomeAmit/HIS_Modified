package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.CalciumPatientHourly;

import java.util.List;

public class CalciumPatientDataResp {
    @SerializedName("calciumPatientHourly")
    @Expose
    public List<CalciumPatientHourly> calciumPatientHourly = null;

    public List<CalciumPatientHourly> getCalciumPatientHourly() {
        return calciumPatientHourly;
    }
}
