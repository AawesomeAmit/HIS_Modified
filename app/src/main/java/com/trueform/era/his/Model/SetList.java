package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetList {
    @SerializedName("setID")
    @Expose
    public Integer setID;
    @SerializedName("setName")
    @Expose
    public String setName;

    public Integer getSetID() {
        return setID;
    }

    public String getSetName() {
        return setName;
    }

    @Override
    public String toString() {
        return setName;
    }
}
