package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table2 {
    @SerializedName("nutrientValue")
    @Expose
    public Float nutrientValue;
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;
    @SerializedName("isIntake")
    @Expose
    public Boolean isIntake;
    @SerializedName("problem")
    @Expose
    public Object problem;

    public Float getNutrientValue() {
        return nutrientValue;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }

    public String getValueTime() {
        return valueTime;
    }

    public Boolean getIntake() {
        return isIntake;
    }

    public Object getProblem() {
        return problem;
    }
}
