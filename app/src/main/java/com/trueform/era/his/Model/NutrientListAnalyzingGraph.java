package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutrientListAnalyzingGraph {
    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("nutrientValue")
    @Expose
    public Double nutrientValue;
    @SerializedName("nutrientValueP")
    @Expose
    public Double nutrientValueP;
    @SerializedName("graphDateTime")
    @Expose
    public String graphDateTime;
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;
    @SerializedName("nutrientRDA")
    @Expose
    public Double nutrientRDA;
    @SerializedName("isIntake")
    @Expose
    public Integer isIntake;

    public String getNutrientName() {
        return nutrientName;
    }

    public Integer getNutrientID() {
        return nutrientID;
    }

    public Double getNutrientValue() {
        return nutrientValue;
    }

    public Double getNutrientValueP() {
        return nutrientValueP;
    }

    public String getGraphDateTime() {
        return graphDateTime;
    }

    public String getValueTime() {
        return valueTime;
    }

    public Double getNutrientRDA() {
        return nutrientRDA;
    }

    public Integer getIsIntake() {
        return isIntake;
    }
}
