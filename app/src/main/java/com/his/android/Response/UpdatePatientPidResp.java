package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.UpdatePatientPidModel;

import java.util.List;

public class UpdatePatientPidResp {
    public List<UpdatePatientPidModel> getPatientExistance() {
        return patientExistance;
    }

    @SerializedName("patientExistance")
    @Expose
    public List<UpdatePatientPidModel> patientExistance = null;
}
