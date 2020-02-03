package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.GetNutrientInBodyModel;

import java.util.List;

public class GetNutrientInBodyResp {
    @SerializedName("resultList")
    @Expose
    private List<GetNutrientInBodyModel> resultList = null;

    public List<GetNutrientInBodyModel> getResultList() {
        return resultList;
    }

}
