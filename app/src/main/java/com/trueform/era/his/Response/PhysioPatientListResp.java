package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PhysioPatientList;

import java.util.List;

public class PhysioPatientListResp {
    @SerializedName("physioPatientList")
    @Expose
    public List<PhysioPatientList> physioPatientList = null;

    public List<PhysioPatientList> getPhysioPatientList() {
        return physioPatientList;
    }
}
