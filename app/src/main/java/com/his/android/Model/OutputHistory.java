package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutputHistory {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("otID")
    @Expose
    public Integer otID;
    @SerializedName("outputDate")
    @Expose
    public String outputDate;
    @SerializedName("typeName")
    @Expose
    public String typeName;
    @SerializedName("quantity")
    @Expose
    public Float quantity;
    @SerializedName("unitname")
    @Expose
    public String unitname;
    @SerializedName("minRange")
    @Expose
    public Object minRange;
    @SerializedName("maxRange")
    @Expose
    public Object maxRange;
    @SerializedName("unitID")
    @Expose
    public Object unitID;
    @SerializedName("t")
    @Expose
    public String t;

    public Integer getId() {
        return id;
    }

    public Integer getOtID() {
        return otID;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getUnitname() {
        return unitname;
    }

    public Object getMinRange() {
        return minRange;
    }

    public Object getMaxRange() {
        return maxRange;
    }

    public Object getUnitID() {
        return unitID;
    }

    public String getT() {
        return t;
    }
}
