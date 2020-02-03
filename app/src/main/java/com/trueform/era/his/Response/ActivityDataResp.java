package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ActivityList;

import java.util.List;

public class ActivityDataResp {
    @SerializedName("activityList")
    @Expose
    public List<ActivityList> activityList = null;

    public List<ActivityList> getActivityList() {
        return activityList;
    }
}
