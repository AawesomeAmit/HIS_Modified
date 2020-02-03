package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Table;
import com.trueform.era.his.Model.Table1;
import com.trueform.era.his.Model.Table2;

import java.util.List;

public class PatientNutrientGraphResp {
    @SerializedName("table")
    @Expose
    public List<Table> table = null;
    @SerializedName("table1")
    @Expose
    public List<Table1> table1 = null;
    @SerializedName("table2")
    @Expose
    public List<Table2> table2 = null;

    public List<Table> getTable() {
        return table;
    }

    public List<Table1> getTable1() {
        return table1;
    }

    public List<Table2> getTable2() {
        return table2;
    }
}
