package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.VersionCheck;

import java.util.List;

public class VersionCheckResp {
    @SerializedName("isVersionUpdated")
    @Expose
    public List<VersionCheck> isVersionUpdated = null;

    public List<VersionCheck> getIsVersionUpdated() {
        return isVersionUpdated;
    }

    public void setIsVersionUpdated(List<VersionCheck> isVersionUpdated) {
        this.isVersionUpdated = isVersionUpdated;
    }
}
