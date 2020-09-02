package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Frequency {
    @SerializedName("name")
    @Expose
    public String name;

    public String getName() {
        return name;
    }
}
