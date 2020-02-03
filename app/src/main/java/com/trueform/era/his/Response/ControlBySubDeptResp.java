package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AssignContol;
import com.trueform.era.his.Model.ConsultantName;

import java.util.List;

public class ControlBySubDeptResp {
    @SerializedName("assignContols")
    @Expose
    public List<AssignContol> assignContols = null;
    @SerializedName("invPackage")
    @Expose
    public List<Object> invPackage = null;
    @SerializedName("consultantName")
    @Expose
    public List<ConsultantName> consultantName = null;

    public List<ConsultantName> getConsultantName() {
        return consultantName;
    }
}
