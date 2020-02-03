package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Dosages;
import com.trueform.era.his.Model.DrugUnit;
import com.trueform.era.his.Model.Frequency;
import com.trueform.era.his.Model.GetIcdCodeModel;
import com.trueform.era.his.Model.Investigation;

import java.util.List;

public class GetIcdCodeResp {

    @SerializedName("icdList")
    @Expose
    public List<GetIcdCodeModel> icdList = null;

    public List<GetIcdCodeModel> getIcdList() {
        return icdList;
    }
}
