package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Dosages;
import com.trueform.era.his.Model.DrugUnit;
import com.trueform.era.his.Model.Frequency;

import java.util.List;

public class FreqUnitResp {
    @SerializedName("frequency")
    @Expose
    public List<Frequency> frequency = null;
    @SerializedName("drugUnit")
    @Expose
    public List<DrugUnit> drugUnit = null;
    @SerializedName("dosages")
    @Expose
    public List<Dosages> dosages = null;

    public List<Frequency> getFrequency() {
        return frequency;
    }

    public List<DrugUnit> getDrugUnit() {
        return drugUnit;
    }

    public List<Dosages> getDosages() {
        return dosages;
    }
}
