package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDiagnosisOutputListModel {
    @SerializedName("pmID")
    @Expose
    private Integer pmID;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("maxOutputDate")
    @Expose
    private String maxOutputDate;

    public Integer getPmID() {
        return pmID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMaxOutputDate() {
        return maxOutputDate;
    }
}
