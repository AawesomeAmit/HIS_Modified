package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalChart {
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("created_Date")
    @Expose
    public String created_Date;
    @SerializedName("headDept")
    @Expose
    public Integer headDept;
    @SerializedName("height")
    @Expose
    public String height;
    @SerializedName("weight")
    @Expose
    public String weight;
    @SerializedName("heartRate")
    @Expose
    public String heartRate;
    @SerializedName("spo2")
    @Expose
    public String spo2;
    @SerializedName("pulse")
    @Expose
    public String pulse;
    @SerializedName("bP_Sys")
    @Expose
    public String sys;
    @SerializedName("temperature")
    @Expose
    public String temperature;
    @SerializedName("bP_Dias")
    @Expose
    public String dys;
    @SerializedName("respRate")
    @Expose
    public String respRate;
    @SerializedName("limbLength")
    @Expose
    public String limbLength;
    @SerializedName("deformity")
    @Expose
    public String deformity;
    @SerializedName("rbs")
    @Expose
    public String rbs;

    public Integer getPmID() {
        return pmID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCreated_Date() {
        return created_Date;
    }

    public Integer getHeadDept() {
        return headDept;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getPulse() {
        return pulse;
    }

    public String getSys() {
        return sys;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDys() {
        return dys;
    }

    public String getRespRate() {
        return respRate;
    }

    public String getLimbLength() {
        return limbLength;
    }

    public String getDeformity() {
        return deformity;
    }

    public String getRbs() {
        return rbs;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getSpo2() {
        return spo2;
    }
}
