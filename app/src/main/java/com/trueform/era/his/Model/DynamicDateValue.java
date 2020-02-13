package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DynamicDateValue {
    @SerializedName("vitalDetail")
    @Expose
    public List<CalciumVitalReport> vitalDetail;
    @SerializedName("investigationDetail")
    @Expose
    public String investigationDetail;
    @SerializedName("foodIntakeDetail")
    @Expose
    public String foodIntakeDetail;
    @SerializedName("medicineIntakeDetail")
    @Expose
    public String medicineIntakeDetail;
    @SerializedName("deathDetail")
    @Expose
    public String deathDetail;
    @SerializedName("nutritionDetail")
    @Expose
    public String nutritionDetail;

    public List<CalciumVitalReport> getVitalDetail() {
        return vitalDetail;
    }

    public String getInvestigationDetail() {
        return investigationDetail;
    }

    public String getFoodIntakeDetail() {
        return foodIntakeDetail;
    }

    public String getMedicineIntakeDetail() {
        return medicineIntakeDetail;
    }

    public String getDeathDetail() {
        return deathDetail;
    }

    public String getNutritionDetail() {
        return nutritionDetail;
    }
}
