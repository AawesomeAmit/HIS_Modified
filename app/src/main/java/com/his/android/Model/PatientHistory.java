package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientHistory {
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("pdmId")
    @Expose
    public Integer pdmId;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("detailID")
    @Expose
    public Integer detailID;
    @SerializedName("nameMaster")
    @Expose
    public String nameMaster;

    public Integer getPmID() {
        return pmID;
    }

    public Integer getPdmId() {
        return pdmId;
    }

    public String getDetails() {
        return details;
    }

    public Integer getDetailID() {
        return detailID;
    }

    public String getNameMaster() {
        return nameMaster;
    }
}
