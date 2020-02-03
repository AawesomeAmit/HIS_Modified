package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymptomList {
    @SerializedName("symptomName")
    @Expose
    public String symptomName;

    public String getSymptomName() {
        return symptomName;
    }
}
