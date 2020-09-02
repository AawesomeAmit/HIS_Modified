package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplimentDetail {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("itID")
    @Expose
    public Integer itID;
    @SerializedName("intakeDate")
    @Expose
    public String intakeDate;
    @SerializedName("typeName")
    @Expose
    public String typeName;
    @SerializedName("quantity")
    @Expose
    public Float quantity;
    @SerializedName("unitname")
    @Expose
    public String unitname;
    @SerializedName("displayName")
    @Expose
    public Object displayName;
    @SerializedName("minRange")
    @Expose
    public Object minRange;
    @SerializedName("maxRange")
    @Expose
    public Object maxRange;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;

    public Integer getId() {
        return id;
    }

    public Integer getItID() {
        return itID;
    }

    public String getIntakeDate() {
        return intakeDate;
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

    public Object getDisplayName() {
        return displayName;
    }

    public Object getMinRange() {
        return minRange;
    }

    public Object getMaxRange() {
        return maxRange;
    }

    public Integer getUnitID() {
        return unitID;
    }
}
