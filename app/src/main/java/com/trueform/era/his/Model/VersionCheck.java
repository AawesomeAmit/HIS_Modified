package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCheck {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("appURL")
    @Expose
    public String appURL;

    public VersionCheck() {
        this.status = false;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getAppURL() {
        return appURL;
    }
}
