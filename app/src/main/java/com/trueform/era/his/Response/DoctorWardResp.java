package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DoctorsList;
import com.trueform.era.his.Model.WardNameList;

import java.util.List;

public class DoctorWardResp {
    @SerializedName("doctorsList")
    @Expose
    public List<DoctorsList> doctorsList = null;
    @SerializedName("wardList")
    @Expose
    public List<WardNameList> wardList = null;

    public List<DoctorsList> getDoctorsList() {
        return doctorsList;
    }

    public List<WardNameList> getWardList() {
        return wardList;
    }
}
