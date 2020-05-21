package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.GetIcdCodeModel;

import java.util.List;

public class GetSymptomResp {

    @SerializedName("table")
    @Expose
    public List<GetIcdCodeModel> icdList = null;

    public List<GetIcdCodeModel> getIcdList() {
        return icdList;
    }
}
