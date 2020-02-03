package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("activityName")
    @Expose
    public String activityName;

    public ActivityMaster(Integer id, String activityName) {
        this.id = id;
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return activityName;
    }

    public Integer getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }
}
