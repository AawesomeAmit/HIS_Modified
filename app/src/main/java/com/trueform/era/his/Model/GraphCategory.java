package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphCategory {
    @SerializedName("graphCategory")
    @Expose
    public String graphCategory;
    @SerializedName("unitName")
    @Expose
    public String unitName;

    public String getGraphCategory() {
        return graphCategory;
    }

    public String getUnitName() {
        return unitName;
    }
}
