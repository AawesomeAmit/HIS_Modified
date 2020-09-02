package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.SetList;
import java.util.List;

public class SetIDResp {
    @SerializedName("setList")
    @Expose
    public List<SetList> setList = null;

    public List<SetList> getSetList() {
        return setList;
    }
}
