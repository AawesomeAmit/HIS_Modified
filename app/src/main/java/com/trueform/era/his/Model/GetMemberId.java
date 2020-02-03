package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMemberId {
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

    public GetMemberId(Integer memberId,Integer userLoginId) {
        this.userLoginId = userLoginId;
        this.memberId = memberId;
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
}
