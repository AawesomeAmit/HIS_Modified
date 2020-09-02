package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WardList {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("wardType")
    @Expose
    public Object wardType;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getWardType() {
        return wardType;
    }
}
