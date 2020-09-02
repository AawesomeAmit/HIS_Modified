package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DieteticsPatientList {
    @SerializedName("pid")
    @Expose
    public Integer pID;
    @SerializedName("userLoginId")
    @Expose
    public Integer userLoginId;
    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("isNutritionalPanel")
    @Expose
    public Boolean isNutritionalPanel;
    @SerializedName("feedType")
    @Expose
    public String feedType;

    @Override
    public String toString() {
        return name+" ("+pID+")";
    }

    public DieteticsPatientList(Integer pID, String name, Integer memberId, Integer userLoginId) {
        this.pID = pID;
        this.userLoginId = userLoginId;
        this.memberId = memberId;
        this.name = name;
    }

    public Integer getpID() {
        return pID;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public Boolean getNutritionalPanel() {
        return isNutritionalPanel;
    }

    public String getFeedType() {
        return feedType;
    }
}
