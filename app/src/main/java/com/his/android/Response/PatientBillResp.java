package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GetPatientBill;

import java.util.List;

public class PatientBillResp {
    @SerializedName("table")
    @Expose
    public List<GetPatientBill> getPatientBills = null;

    public List<GetPatientBill> getGetPatientBills() {
        return getPatientBills;
    }
}
