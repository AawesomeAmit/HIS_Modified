package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RangeHistory {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("typeName")
    @Expose
    public String typeName;
    @SerializedName("diagnosis")
    @Expose
    public String diagnosis;
    @SerializedName("minRange")
    @Expose
    public Float minRange;
    @SerializedName("maxRange")
    @Expose
    public Float maxRange;
    @SerializedName("unitName")
    @Expose
    public String unitName;
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

    public String getTypeName() {
        return typeName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Float getMinRange() {
        return minRange;
    }

    public Float getMaxRange() {
        return maxRange;
    }

    public String getUnitName() {
        return unitName;
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
