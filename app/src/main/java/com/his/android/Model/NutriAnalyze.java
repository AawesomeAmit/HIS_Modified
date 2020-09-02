package com.his.android.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutriAnalyze {
    @SerializedName("nutrientID")
    @Expose
    private Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("achievedNutrientValue")
    @Expose
    private String achievedNutrientValue;
    @SerializedName("achievedRDAPercentage")
    @Expose
    private Integer achievedRDAPercentage;
    @SerializedName("achievedRDAColorCode")
    @Expose
    private String achievedRDAColorCode;
    @SerializedName("extraNutrientValue")
    @Expose
    private String extraNutrientValue;
    @SerializedName("extraRDAPercentage")
    @Expose
    private Integer extraRDAPercentage;
    @SerializedName("extraRDAColorCode")
    @Expose
    private String extraRDAColorCode;
    @SerializedName("totalNutrientValue")
    @Expose
    private String totalNutrientValue;
    @SerializedName("totalRDAPercentage")
    @Expose
    private Integer totalRDAPercentage;
    @SerializedName("unitName")
    @Expose
    private String unitName;
    @SerializedName("graphCategory")
    @Expose
    private String graphCategory;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public void setNutrientID(Integer nutrientID) {
        this.nutrientID = nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAchievedNutrientValue() {
        return achievedNutrientValue;
    }

    public void setAchievedNutrientValue(String achievedNutrientValue) {
        this.achievedNutrientValue = achievedNutrientValue;
    }

    public Integer getAchievedRDAPercentage() {
        return achievedRDAPercentage;
    }

    public void setAchievedRDAPercentage(Integer achievedRDAPercentage) {
        this.achievedRDAPercentage = achievedRDAPercentage;
    }

    public String getAchievedRDAColorCode() {
        return achievedRDAColorCode;
    }

    public void setAchievedRDAColorCode(String achievedRDAColorCode) {
        this.achievedRDAColorCode = achievedRDAColorCode;
    }

    public String getExtraNutrientValue() {
        return extraNutrientValue;
    }

    public void setExtraNutrientValue(String extraNutrientValue) {
        this.extraNutrientValue = extraNutrientValue;
    }

    public Integer getExtraRDAPercentage() {
        return extraRDAPercentage;
    }

    public void setExtraRDAPercentage(Integer extraRDAPercentage) {
        this.extraRDAPercentage = extraRDAPercentage;
    }

    public String getExtraRDAColorCode() {
        return extraRDAColorCode;
    }

    public void setExtraRDAColorCode(String extraRDAColorCode) {
        this.extraRDAColorCode = extraRDAColorCode;
    }

    public String getTotalNutrientValue() {
        return totalNutrientValue;
    }

    public void setTotalNutrientValue(String totalNutrientValue) {
        this.totalNutrientValue = totalNutrientValue;
    }

    public Integer getTotalRDAPercentage() {
        return totalRDAPercentage;
    }

    public void setTotalRDAPercentage(Integer totalRDAPercentage) {
        this.totalRDAPercentage = totalRDAPercentage;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGraphCategory() {
        return graphCategory;
    }

    public void setGraphCategory(String graphCategory) {
        this.graphCategory = graphCategory;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
