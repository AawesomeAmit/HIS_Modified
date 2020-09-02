package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DischargePatient {
    @SerializedName("pmid")
    @Expose
    public Integer pmid;
    @SerializedName("wardID")
    @Expose
    public Integer wardID;
    @SerializedName("previousWardID")
    @Expose
    public Integer previousWardID;
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("userID")
    @Expose
    public Integer userID;
    @SerializedName("subDeptID")
    @Expose
    public Integer subDeptID;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("consultantName")
    @Expose
    public String consultantName;
    @SerializedName("pname")
    @Expose
    public String pname;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("age")
    @Expose
    public Float age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("fatherName")
    @Expose
    public String fatherName;
    @SerializedName("phoneNo")
    @Expose
    public String phoneNo;
    @SerializedName("previousWardName")
    @Expose
    public String previousWardName;
}
