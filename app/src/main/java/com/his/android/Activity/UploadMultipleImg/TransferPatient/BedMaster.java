package com.his.android.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BedMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("bedName")
    @Expose
    public String bedName;

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return bedName;
    }

    public String getBedName() {
        return bedName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }
}
