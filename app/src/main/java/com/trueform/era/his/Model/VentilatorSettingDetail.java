package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VentilatorSettingDetail {
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("volume")
    @Expose
    public Integer volume;
    @SerializedName("breathRate")
    @Expose
    public Integer breathRate;

    public Double getPressure() {
        return pressure;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getBreathRate() {
        return breathRate;
    }
}
