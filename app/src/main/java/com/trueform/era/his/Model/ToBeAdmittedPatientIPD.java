package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToBeAdmittedPatientIPD {

    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("ipNo")
    @Expose
    public String ipNo;
    @SerializedName("crNo")
    @Expose
    public String crNo;
    @SerializedName("pname")
    @Expose
    public String pname;
    @SerializedName("sex")
    @Expose
    public String sex;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("consultantName")
    @Expose
    public String consultantName;
    @SerializedName("consultantID")
    @Expose
    public Integer consultantID;
    @SerializedName("wardID")
    @Expose
    public Integer wardID;

    public Integer getPid() {
        return pid;
    }

    public String getIpNo() {
        return ipNo;
    }

    public String getCrNo() {
        return crNo;
    }

    public String getPname() {
        return pname;
    }

    public String getSex() {
        return sex;
    }

    public String getGender() {
        return gender;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Integer getConsultantID() {
        return consultantID;
    }

    public Integer getWardID() {
        return wardID;
    }
}
