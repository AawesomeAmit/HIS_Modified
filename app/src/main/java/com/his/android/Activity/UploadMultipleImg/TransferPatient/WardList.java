package com.his.android.Activity.UploadMultipleImg.TransferPatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WardList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("wardType")
    @Expose
    public String wardType;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWardType() {
        return wardType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }

    @Override
    public String toString() {
        return name;
    }
}
