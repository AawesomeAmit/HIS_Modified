package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalListNutrient {
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("bP_Dias")
    @Expose
    public String bPDias;
    @SerializedName("pulse")
    @Expose
    public String pulse;
    @SerializedName("bP_Sys")
    @Expose
    public String bPSys;
    @SerializedName("rR_Sp")
    @Expose
    public String rr_sp;
    @SerializedName("pV_Sp")
    @Expose
    public String pv_sp;
    @SerializedName("respRate")
    @Expose
    public String respRate;
    @SerializedName("temperature")
    @Expose
    public String temperature;
    @SerializedName("spo2")
    @Expose
    public String spo2;
    @SerializedName("heartRate")
    @Expose
    public String heartRate;

    public String getPv_sp() {
        return pv_sp;
    }

    public String getTime() {
        return time;
    }

    public String getbPDias() {
        return bPDias;
    }

    public String getPulse() {
        return pulse;
    }

    public String getbPSys() {
        return bPSys;
    }

    public String getRespRate() {
        return respRate;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRr_sp() {
        return rr_sp;
    }

    public String getSpo2() {
        return spo2;
    }

    public String getHeartRate() {
        return heartRate;
    }
}
