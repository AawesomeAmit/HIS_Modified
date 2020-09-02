package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNutrientLevelImmediateEffectByFoodTimeId {
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("nutrientLevel")
    @Expose
    public String nutrientLevel;
    @SerializedName("symptom")
    @Expose
    public String symptom;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getNutrientLevel() {
        return nutrientLevel;
    }

    public String getSymptom() {
        return symptom;
    }
}
