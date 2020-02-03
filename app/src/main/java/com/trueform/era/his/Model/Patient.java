package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("pname")
    @Expose
    public String pname;
    @SerializedName("sex")
    @Expose
    public String sex;
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
    @SerializedName("phoneNo")
    @Expose
    public String phoneNo;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
}
