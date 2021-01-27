package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleSave {
    @SerializedName("column1")
    @Expose
    public String column1;

    public String getColumn1() {
        return column1;
    }
}
