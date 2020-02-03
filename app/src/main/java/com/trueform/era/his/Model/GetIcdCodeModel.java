package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIcdCodeModel {
    @SerializedName("detailID")
    @Expose
    public Integer detailID;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("pdmID")
    @Expose
    public Integer pdmID;

    public Integer getDetailID() {
        return detailID;
    }

    public String getDetails() {
        return details;
    }

    public Integer getPdmID() {
        return pdmID;
    }

    public void setDetailID(Integer detailID) {
        this.detailID = detailID;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPdmID(Integer pdmID) {
        this.pdmID = pdmID;
    }

    @Override
    public String toString() {
        return details;
    }
}
