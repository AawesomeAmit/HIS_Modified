package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Ward;

import java.util.List;

public class WardResp {
    @SerializedName("wardTransferList")
    @Expose
    public List<Ward> wardTransferList;

    public List<Ward> getWardTransferList() {
        return wardTransferList;
    }
}
