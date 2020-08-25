package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WardNameList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("wardName")
    @Expose
    public String wardName;

    @Override
    public String toString() {
        return wardName;
    }
    public Integer getId() {
        return id;
    }

    public WardNameList(Integer id, String wardName) {
        this.id = id;
        this.wardName = wardName;
    }

    public String getWardName() {
        return wardName;
    }
}
