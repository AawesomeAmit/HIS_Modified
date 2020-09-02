package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.NutrientListAnalyzingGraph;
import com.his.android.Model.NutrientTable;
import com.his.android.Model.VitalAutoCompleteModel;
import com.his.android.Model.VitalTable;

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
