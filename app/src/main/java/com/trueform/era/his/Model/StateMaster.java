package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("stateName")
    @Expose
    public String stateName;

    @Override
    public String toString() {
        return stateName;
    }
    public Integer getId() {
        return id;
    }

    public StateMaster(Integer id, String stateName) {
        this.id = id;
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
