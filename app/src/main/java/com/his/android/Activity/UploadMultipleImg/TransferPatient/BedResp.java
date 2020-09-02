package com.his.android.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BedResp {
    @SerializedName("bedMaster")
    @Expose
    public List<BedMaster> bedMaster = null;

    public List<BedMaster> getBedMaster() {
        return bedMaster;
    }
}
