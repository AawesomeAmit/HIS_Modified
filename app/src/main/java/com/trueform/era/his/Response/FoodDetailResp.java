package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.FoodDetail;

import java.util.List;

public class FoodDetailResp {
    @SerializedName("foodDetail")
    @Expose
    public List<FoodDetail> foodDetail = null;

    public List<FoodDetail> getFoodDetail() {
        return foodDetail;
    }
}
