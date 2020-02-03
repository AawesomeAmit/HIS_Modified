package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.GetPatientListModel;

import java.util.List;

public class GetPatientListResp {
    public List<GetPatientListModel> getPatientList() {
        return patientList;
    }

    @SerializedName("patientList")
    @Expose
    public List<GetPatientListModel> patientList = null;

}
