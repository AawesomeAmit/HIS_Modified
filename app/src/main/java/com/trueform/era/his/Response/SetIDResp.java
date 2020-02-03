package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.SetList;
import java.util.List;

public class SetIDResp {
    @SerializedName("setList")
    @Expose
    public List<SetList> setList = null;

    public List<SetList> getSetList() {
        return setList;
    }
}
