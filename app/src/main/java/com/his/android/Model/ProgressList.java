package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("consultant")
    @Expose
    public String consultant;

    public Integer getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getTime() {
        return time;
    }

    public String getConsultant() {
        return consultant;
    }
}
