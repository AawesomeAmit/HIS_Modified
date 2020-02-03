package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeadAssign {
    @SerializedName("headID")
    @Expose
    private Integer headID;
    @SerializedName("headName")
    @Expose
    private String headName;
    @SerializedName("headDiscription")
    @Expose
    private String headDiscription;
    @SerializedName("ctrl")
    @Expose
    public String ctrl;
    @SerializedName("pageURL")
    @Expose
    public String pageURL;
    @SerializedName("color")
    @Expose
    public String color;

    public HeadAssign(Integer headID, String headName, String color) {
        this.headID = headID;
        this.headName = headName;
        this.color=color;
    }

    public Integer getHeadID() {
        return headID;
    }

    public String getHeadName() {
        return headName;
    }

    public String getHeadDiscription() {
        return headDiscription;
    }

    public String getColor() {
        return color;
    }
}