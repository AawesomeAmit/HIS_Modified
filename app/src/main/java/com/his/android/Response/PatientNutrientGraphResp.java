package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Table;
import com.his.android.Model.Table1;
import com.his.android.Model.Table2;

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
