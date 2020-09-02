package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SumTotalInOutput {
    @SerializedName("datefinal")
    @Expose
    public String datefinal;
    @SerializedName("quantity")
    @Expose
    public Float quantity;
    @SerializedName("typeName")
    @Expose
    public String typeName;

    public String getDatefinal() {
        return datefinal;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getTypeName() {
        return typeName;
    }
}
