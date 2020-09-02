package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewNotificationResp {
    @SerializedName("notificationID")
    @Expose
    public Integer notificationID;
    @SerializedName("notificationBody")
    @Expose
    public String notificationBody;
    @SerializedName("notificationTitle")
    @Expose
    public String notificationTitle;
    @SerializedName("notificationTypeID")
    @Expose
    public Integer notificationTypeID;
    @SerializedName("notificationDate")
    @Expose
    public String notificationDate;
    @SerializedName("isRead")
    @Expose
    public Boolean isRead;

    public Integer getNotificationID() {
        return notificationID;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public Integer getNotificationTypeID() {
        return notificationTypeID;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public Boolean getRead() {
        return isRead;
    }
}
