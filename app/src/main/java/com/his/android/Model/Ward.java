package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ward {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("shortName")
    @Expose
    public String shortName;
    @SerializedName("headId")
    @Expose
    public Integer headId;

    public Integer getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public Integer getHeadId() {
        return headId;
    }

    @Override
    public String toString() {
        return shortName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }
}
