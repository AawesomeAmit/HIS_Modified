package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestigationList {
    @SerializedName("investigationName")
    @Expose
    public String investigationName;
    @SerializedName("investigationResult")
    @Expose
    public String investigationResult;

    public String getInvestigationName() {
        return investigationName;
    }

    public String getInvestigationResult() {
        return investigationResult;
    }
}
