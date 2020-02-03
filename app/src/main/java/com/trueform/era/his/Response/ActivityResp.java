package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ActivityMaster;

import java.util.List;

public class ActivityResp {
    @SerializedName("activityMaster")
    @Expose
    public List<ActivityMaster> activityMaster = null;

    public List<ActivityMaster> getActivityMaster() {
        return activityMaster;
    }
}
