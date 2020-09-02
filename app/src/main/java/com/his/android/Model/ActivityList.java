package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityList {
    @SerializedName("physicalActivityID")
    @Expose
    public Integer physicalActivityID;
    @SerializedName("activityID")
    @Expose
    public Integer activityID;
    @SerializedName("activityName")
    @Expose
    public String activityName;
    @SerializedName("timeFrom")
    @Expose
    public String timeFrom;
    @SerializedName("timeTo")
    @Expose
    public String timeTo;
    @SerializedName("rating")
    @Expose
    public Integer rating;

    public Integer getPhysicalActivityID() {
        return physicalActivityID;
    }

    public Integer getActivityID() {
        return activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public Integer getRating() {
        return rating;
    }
}
