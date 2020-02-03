package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdmittedPatientICU {
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
    @SerializedName("bedName")
    @Expose
    public String bedName;
    @SerializedName("subDepartmentName")
    @Expose
    public String subDepartmentName;
    @SerializedName("dischargeDate")
    @Expose
    public String dischargeDate;
    @SerializedName("dischargeStatus")
    @Expose
    public String dischargeStatus;

    public Integer getNotificationCount() {
        return notificationCount;
    }

    @SerializedName("notificationCount")
    @Expose
    public Integer notificationCount;

    public String getDischargeStatus() {
        return dischargeStatus;
    }

    public String getDdate() {
        return ddate;
    }

    @SerializedName("ddate")
    @Expose
    public String ddate;

    public String getDischargeDate() {
        return dischargeDate;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    @SerializedName("admitDate")
    @Expose
    public String admitDate;

    public String getRelation() {
        return relation;
    }

    @SerializedName("relation")
    @Expose
    public String relation;

    public String getSex() {
        return sex;
    }

    @SerializedName("sex")
    @Expose
    public String sex;

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public String getBedName() {
        return bedName;
    }

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
    public String age;
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
    @NonNull
    @Override
    public String toString() {
        return pname+"("+pid.toString()+")";
    }

    public AdmittedPatientICU(Integer pid, String pname, String gender, String age, String ageUnit, Integer subDeptID) {
        this.pid = pid;
        this.pname = pname;
        this.gender = gender;
        this.age = age;
        this.ageUnit = ageUnit;
        this.subDeptID=subDeptID;
    }

    public Integer getPmid() {
        return pmid;
    }

    public Integer getWardID() {
        return wardID;
    }

    public Integer getPreviousWardID() {
        return previousWardID;
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

    public Integer getUserID() {
        return userID;
    }

    public Integer getSubDeptID() {
        return subDeptID;
    }

    public Integer getStatus() {
        return status;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public String getPname() {
        return pname;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getAddress() {
        return address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPreviousWardName() {
        return previousWardName;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public void setWardID(Integer wardID) {
        this.wardID = wardID;
    }

    public void setPreviousWardID(Integer previousWardID) {
        this.previousWardID = previousWardID;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCrNo(String crNo) {
        this.crNo = crNo;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public void setSubDepartmentName(String subDepartmentName) {
        this.subDepartmentName = subDepartmentName;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public void setDischargeStatus(String dischargeStatus) {
        this.dischargeStatus = dischargeStatus;
    }

    public void setNotificationCount(Integer notificationCount) {
        this.notificationCount = notificationCount;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setIpNo(String ipNo) {
        this.ipNo = ipNo;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setSubDeptID(Integer subDeptID) {
        this.subDeptID = subDeptID;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPreviousWardName(String previousWardName) {
        this.previousWardName = previousWardName;
    }
}
