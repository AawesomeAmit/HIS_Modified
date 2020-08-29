package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdmittedPatient {
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("crNo")
    @Expose
    private String crNo;
    @SerializedName("ipNo")
    @Expose
    private String ipNo;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("subDeptID")
    @Expose
    private Integer subDeptID;
    @SerializedName("consultantName")
    @Expose
    private String consultantName;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("ageUnit")
    @Expose
    private String ageUnit;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("pmid")
    @Expose
    public Integer pmid;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("fatherName1")
    @Expose
    private String fatherName1;
    @SerializedName("wardID")
    @Expose
    private Integer wardID;
    @SerializedName("wardName")
    @Expose
    private String wardName;
    @SerializedName("isRead")
    @Expose
    public Boolean isRead;
    @NonNull
    @Override
    public String toString() {
        return pname+"("+pid.toString()+")";
    }

    public AdmittedPatient(Integer pid, Integer subDeptID, String pname, String age, String ageUnit, String sex, String gender) {
        this.pid = pid;
        this.subDeptID = subDeptID;
        this.pname = pname;
        this.age = age;
        this.ageUnit = ageUnit;
        this.sex = sex;
        this.gender = gender;
    }

    public Integer getPid() {
        return pid;
    }

    public String getCrNo() {
        return crNo;
    }

    public String getIpNo() {
        return ipNo;
    }

    public Integer getPmid() {
        return pmid;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getSubDeptID() {
        return subDeptID;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public String getPname() {
        return pname;
    }

    public String getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getAddress() {
        return address;
    }

    public String getSex() {
        return sex;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getRelation() {
        return relation;
    }

    public String getFatherName1() {
        return fatherName1;
    }

    public Integer getWardID() {
        return wardID;
    }

    public String getWardName() {
        return wardName;
    }

    public Boolean getRead() {
        return isRead;
    }
}
