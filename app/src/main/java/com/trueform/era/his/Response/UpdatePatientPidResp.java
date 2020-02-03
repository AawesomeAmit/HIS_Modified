package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AttributeValueList;
import com.trueform.era.his.Model.UpdatePatientPidModel;

import java.util.List;

public class UpdatePatientPidResp {
    public List<UpdatePatientPidModel> getPatientExistance() {
        return patientExistance;
    }

    @SerializedName("patientExistance")
    @Expose
    public List<UpdatePatientPidModel> patientExistance = null;
}
