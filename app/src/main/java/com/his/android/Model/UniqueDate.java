package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniqueDate {
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("cdate")
    @Expose
    public String cdate;

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCdate() {
        return cdate;
    }
}
