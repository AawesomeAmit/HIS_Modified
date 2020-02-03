package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDiagnosisIntakeListModel {
    @SerializedName("pmID")
    @Expose
    private Integer pmID;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("maxIntakeDate")
    @Expose
    private String maxIntakeDate;

    public Integer getPmID() {
        return pmID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMaxIntakeDate() {
        return maxIntakeDate;
    }
}
