package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SideEffectList {
    @SerializedName("sideEffectName")
    @Expose
    private String SideEffectName;

    public String getSideEffectName() {
        return SideEffectName;
    }
}
