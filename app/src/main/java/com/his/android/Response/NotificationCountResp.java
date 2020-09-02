package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCountResp {
    @SerializedName("unReadCount")
    @Expose
    public Integer unReadCount;

    public Integer getUnReadCount() {
        return unReadCount;
    }
}
