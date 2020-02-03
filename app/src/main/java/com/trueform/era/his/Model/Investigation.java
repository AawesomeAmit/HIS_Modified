package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Investigation {
    @SerializedName("itemID")
    @Expose
    public Integer itemID;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("amt")
    @Expose
    public Integer amt;
    @SerializedName("ischecked")
    @Expose
    public Boolean ischecked;
    @SerializedName("remark")
    @Expose
    public String remark;
    @NonNull
    @Override
    public String toString() {
        return itemName;
    }

    public Investigation(Integer itemID, String itemName, Integer amt, String remark, Boolean ischecked) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.amt = amt;
        this.remark=remark;
        this.ischecked = ischecked;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public Integer getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getAmt() {
        return amt;
    }

    public String getRemark() {
        return remark;
    }

    public Boolean getIschecked() {
        return ischecked;
    }
}
