package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GetNutrientInBodyModel;

import java.util.List;

public class GetNutrientInBodyResp {
    @SerializedName("resultList")
    @Expose
    private List<GetNutrientInBodyModel> resultList = null;

    public List<GetNutrientInBodyModel> getResultList() {
        return resultList;
    }

}
