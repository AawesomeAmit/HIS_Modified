package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeDashboard {
    @SerializedName("dietID")
    @Expose
    public Integer dietID;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("givenIntakeQuantity")
    @Expose
    public String givenIntakeQuantity;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("isSupplement")
    @Expose
    public Integer isSupplement;

    public Integer getDietID() {
        return dietID;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public String getGivenIntakeQuantity() {
        return givenIntakeQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getIsSupplement() {
        return isSupplement;
    }
}
