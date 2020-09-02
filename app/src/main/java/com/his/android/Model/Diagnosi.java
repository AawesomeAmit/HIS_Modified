package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diagnosi {
    @SerializedName("detailID")
    @Expose
    public Integer detailID;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("pdmID")
    @Expose
    public Integer pdmID;
    @SerializedName("gender")
    @Expose
    public String gender;
}
