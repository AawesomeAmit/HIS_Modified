package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.NutrientListAnalyzingGraph;
import com.trueform.era.his.Model.ObservationList;
import com.trueform.era.his.Model.VitalAutoCompleteModel;

import java.util.List;

public class AnalyzingGraphResp {
    @SerializedName("vitalList")
    @Expose
    public List<VitalAutoCompleteModel> vitalList = null;
    @SerializedName("nutrientList")
    @Expose
    public List<NutrientListAnalyzingGraph> nutrientList = null;

    public List<VitalAutoCompleteModel> getVitalList() {
        return vitalList;
    }

    public List<NutrientListAnalyzingGraph> getNutrientList() {
        return nutrientList;
    }
}
