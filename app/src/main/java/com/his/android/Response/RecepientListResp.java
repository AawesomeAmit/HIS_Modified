package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.RecepientList;

import java.util.List;

public class RecepientListResp {
    @SerializedName("recepientList")
    @Expose
    public List<RecepientList> recepientList = null;

    public List<RecepientList> getRecepientList() {
        return recepientList;
    }
}
