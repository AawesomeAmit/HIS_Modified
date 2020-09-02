package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GetIcdCodeModel;

import java.util.List;

public class GetIcdCodeResp {

    @SerializedName("icdList")
    @Expose
    public List<GetIcdCodeModel> icdList = null;

    public List<GetIcdCodeModel> getIcdList() {
        return icdList;
    }
}
