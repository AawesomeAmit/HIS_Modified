package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.SupplimentDetail;

import java.util.List;

public class SupplementDetailResp {
    @SerializedName("supplimentDetail")
    @Expose
    public List<SupplimentDetail> supplimentDetail = null;

    public List<SupplimentDetail> getSupplimentDetail() {
        return supplimentDetail;
    }
}
