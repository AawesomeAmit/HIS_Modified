package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrition {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;

    public Nutrition(Integer id, String nutrientName) {
        this.id = id;
        this.nutrientName = nutrientName;
    }

    public Integer getId() {
        return id;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    @Override
    public String toString() {
        return nutrientName;
    }
}
