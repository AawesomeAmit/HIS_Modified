package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSupplementResp {
    @SerializedName("previousList")
    @Expose
    public String previousList;
    @SerializedName("pendingList")
    @Expose
    public String pendingList;
    @SerializedName("upcomingList")
    @Expose
    public String upcomingList;

    public String getPreviousList() {
        return previousList;
    }

    public String getPendingList() {
        return pendingList;
    }

    public String getUpcomingList() {
        return upcomingList;
    }
}
