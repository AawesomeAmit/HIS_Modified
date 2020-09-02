package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientBill {
    @SerializedName("billDate")
    @Expose
    public String billDate;
    @SerializedName("billNo")
    @Expose
    public String billNo;
    @SerializedName("billMasterID")
    @Expose
    public Integer billMasterID;
    @SerializedName("ttype")
    @Expose
    public String ttype;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("categoryID")
    @Expose
    public String categoryID;

    public String getBillDate() {
        return billDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public Integer getBillMasterID() {
        return billMasterID;
    }

    public String getTtype() {
        return ttype;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCategoryID() {
        return categoryID;
    }
}
