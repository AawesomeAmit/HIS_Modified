package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VentilatorData {
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("volume")
    @Expose
    public Integer volume;
    @SerializedName("breathRate")
    @Expose
    public Integer breathRate;
    @SerializedName("temperature")
    @Expose
    public Integer temperature;
    @SerializedName("dateTime")
    @Expose
    public String dateTime;

    public Double getPressure() {
        return pressure;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getBreathRate() {
        return breathRate;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public String getDateTime() {
        return dateTime;
    }
}
