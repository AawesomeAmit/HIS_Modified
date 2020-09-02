package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ActivityMaster;

import java.util.List;

public class ActivityResp {
    @SerializedName("activityMaster")
    @Expose
    public List<ActivityMaster> activityMaster = null;

    public List<ActivityMaster> getActivityMaster() {
        return activityMaster;
    }
}
