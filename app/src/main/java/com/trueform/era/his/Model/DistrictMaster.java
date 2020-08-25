package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictMaster {
    @SerializedName("districtName")
    @Expose
    public String districtName;
    @SerializedName("id")
    @Expose
    public Integer id;

    public DistrictMaster(Integer id, String districtName) {
        this.districtName = districtName;
        this.id = id;
    }

    @Override
    public String toString() {
        return districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public Integer getId() {
        return id;
    }
}
