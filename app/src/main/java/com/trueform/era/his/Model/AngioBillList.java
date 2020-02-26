package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AngioBillList {
    @SerializedName("billNo")
    @Expose
    public String billNo;

    public String getBillNo() {
        return billNo;
    }

    @Override
    public String toString() {
        return billNo;
    }

    public AngioBillList(String billNo) {
        this.billNo = billNo;
    }
}
