package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AngioReportList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("billNo")
    @Expose
    public String billNo;
    @SerializedName("cathID")
    @Expose
    public String cathID;
    @SerializedName("itemID")
    @Expose
    public Integer itemID;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("impression")
    @Expose
    public String impression;
    @SerializedName("resultRemark")
    @Expose
    public String resultRemark;
    @SerializedName("billDate")
    @Expose
    public String billDate;
    @SerializedName("procerureDate")
    @Expose
    public String procerureDate;
    @SerializedName("givenBy")
    @Expose
    public String givenBy;

    public Integer getId() {
        return id;
    }

    public Integer getPid() {
        return pid;
    }

    public String getCrNo() {
        return crNo;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getCathID() {
        return cathID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getImpression() {
        return impression;
    }

    public String getResultRemark() {
        return resultRemark;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getProcerureDate() {
        return procerureDate;
    }

    public String getGivenBy() {
        return givenBy;
    }
}
