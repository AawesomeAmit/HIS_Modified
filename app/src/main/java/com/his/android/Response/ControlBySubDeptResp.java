package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AssignContol;
import com.his.android.Model.ConsultantName;

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
