package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SupplimentDetail;

import java.util.List;

public class SupplementDetailResp {
    @SerializedName("supplimentDetail")
    @Expose
    public List<SupplimentDetail> supplimentDetail = null;

    public List<SupplimentDetail> getSupplimentDetail() {
        return supplimentDetail;
    }
}
