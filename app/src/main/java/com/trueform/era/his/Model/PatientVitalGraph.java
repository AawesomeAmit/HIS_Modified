package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientVitalGraph {
    @SerializedName("bP_Dias")
    @Expose
    public String bP_Dias;
    @SerializedName("bP_Sys")
    @Expose
    public String bP_Sys;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("heartRate")
    @Expose
    public String heartRate;
    @SerializedName("pulse")
    @Expose
    public String pulse;

    public void setbP_Dias(String bP_Dias) {
        this.bP_Dias = bP_Dias;
    }

    public void setbP_Sys(String bP_Sys) {
        this.bP_Sys = bP_Sys;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public void setRespRate(String respRate) {
        this.respRate = respRate;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @SerializedName("respRate")
    @Expose
    public String respRate;
    @SerializedName("spo2")
    @Expose
    public String spo2;
    @SerializedName("hr")
    @Expose
    public Integer hr;
    @SerializedName("x")
    @Expose
    public Integer x;

    public String getbP_Dias() {
        return bP_Dias;
    }

    public String getbP_Sys() {
        return bP_Sys;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getPulse() {
        return pulse;
    }

    public String getRespRate() {
        return respRate;
    }

    public String getSpo2() {
        return spo2;
    }

    public Integer getHr() {
        return hr;
    }

    public Integer getX() {
        return x;
    }
}
