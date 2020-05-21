package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VentilatorList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("machineName")
    @Expose
    public String machineName;

    @Override
    public String toString() {
        return machineName;
    }

    public Integer getId() {
        return id;
    }

    public String getMachineName() {
        return machineName;
    }

    public VentilatorList(Integer id, String machineName) {
        this.id = id;
        this.machineName = machineName;
    }
}
