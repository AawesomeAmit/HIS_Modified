package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowNotificaton {
    @SerializedName("notification")
    @Expose
    public String notification;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("isCritical")
    @Expose
    public Boolean isCritical;
}
