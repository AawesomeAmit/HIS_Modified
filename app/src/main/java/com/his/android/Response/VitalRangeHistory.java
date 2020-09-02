package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalRangeHistory {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("minRange")
    @Expose
    public Float minRange;
    @SerializedName("maxRange")
    @Expose
    public Float maxRange;
    @SerializedName("drName")
    @Expose
    public String drName;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("status")
    @Expose
    public Integer status;

    public Integer getId() {
        return id;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getVitalName() {
        return vitalName;
    }

    public Float getMinRange() {
        return minRange;
    }

    public Float getMaxRange() {
        return maxRange;
    }

    public String getDrName() {
        return drName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Integer getStatus() {
        return status;
    }
}
