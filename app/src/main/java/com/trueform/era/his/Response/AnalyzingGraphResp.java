package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.NutrientListAnalyzingGraph;
import com.trueform.era.his.Model.NutrientTable;
import com.trueform.era.his.Model.VitalAutoCompleteModel;
import com.trueform.era.his.Model.VitalTable;

import java.util.List;

public class AnalyzingGraphResp {
    @SerializedName("vitalList")
    @Expose
    public List<VitalAutoCompleteModel> vitalList = null;
    @SerializedName("nutrientList")
    @Expose
    public List<NutrientListAnalyzingGraph> nutrientList = null;
    @SerializedName("table2")
    @Expose
    public List<NutrientTable> nutrientTableList = null;
    @SerializedName("table3")
    @Expose
    public List<VitalTable> vitalTableList = null;

    public List<VitalTable> getVitalTableList() {
        return vitalTableList;
    }

    public List<NutrientTable> getNutrientTableList() {
        return nutrientTableList;
    }

    public List<VitalAutoCompleteModel> getVitalList() {
        return vitalList;
    }

    public List<NutrientListAnalyzingGraph> getNutrientList() {
        return nutrientList;
    }
}
