package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultList {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("userLoginId")
    @Expose
    public Object userLoginId;
    @SerializedName("memberId")
    @Expose
    public Object memberId;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("admitDate")
    @Expose
    public String admitDate;
    @SerializedName("dischargeDate")
    @Expose
    public String dischargeDate;
    @SerializedName("wardName")
    @Expose
    public String wardName;
    @SerializedName("diagnosis")
    @Expose
    public String diagnosis;
}
