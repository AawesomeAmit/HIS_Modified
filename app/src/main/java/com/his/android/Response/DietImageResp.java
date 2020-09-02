package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DietImageList;

import java.util.List;

public class DietImageResp {
    @SerializedName("dietImageList")
    @Expose
    public List<DietImageList> dietImageList = null;

    public List<DietImageList> getDietImageList() {
        return dietImageList;
    }
}
