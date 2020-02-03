package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Dosages;
import com.trueform.era.his.Model.DrugUnit;
import com.trueform.era.his.Model.Frequency;
import com.trueform.era.his.Model.Investigation;

import java.util.List;

public class AddInvestigationResp {
    @SerializedName("investigation")
    @Expose
    public List<Investigation> investigation = null;
    @SerializedName("frequency")
    @Expose
    public List<Frequency> frequency = null;
    @SerializedName("dosages")
    @Expose
    public List<Dosages> dosages = null;
    @SerializedName("drugUnit")
    @Expose
    public List<DrugUnit> drugUnit = null;

    public List<Investigation> getInvestigation() {
        return investigation;
    }

    public List<Frequency> getFrequency() {
        return frequency;
    }

    public List<DrugUnit> getDrugUnit() {
        return drugUnit;
    }

    public void setInvestigation(List<Investigation> investigation) {
        this.investigation = investigation;
    }

    public List<Dosages> getDosages() {
        return dosages;
    }
}
