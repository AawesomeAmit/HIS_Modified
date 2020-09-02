package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlertToDo {
    @SerializedName("inputCategoryId")
    @Expose
    public Integer inputCategoryId;
    @SerializedName("inputCategory")
    @Expose
    public String inputCategory;
    @SerializedName("outputCategory")
    @Expose
    public String outputCategory;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("advise")
    @Expose
    public String advise;
    @SerializedName("referenceValueFrom")
    @Expose
    public Double referenceValueFrom;
    @SerializedName("referenceValueTo")
    @Expose
    public Double referenceValueTo;
    @SerializedName("unitName")
    @Expose
    public String unitName;

    public Integer getInputCategoryId() {
        return inputCategoryId;
    }

    public String getInputCategory() {
        return inputCategory;
    }

    public String getOutputCategory() {
        return outputCategory;
    }

    public String getReference() {
        return reference;
    }

    public String getAdvise() {
        return advise;
    }

    public Double getReferenceValueFrom() {
        return referenceValueFrom;
    }

    public Double getReferenceValueTo() {
        return referenceValueTo;
    }

    public String getUnitName() {
        return unitName;
    }
}
