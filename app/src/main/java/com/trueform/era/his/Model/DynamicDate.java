package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DynamicDate {
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("value")
    @Expose
    public List<DynamicDateValue> value = null;

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(List<DynamicDateValue> value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public List<DynamicDateValue> getValue() {
        return value;
    }
}
