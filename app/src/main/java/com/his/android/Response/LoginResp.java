package com.his.android.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.HeadAssign;
import com.his.android.Model.OpdDay;
import com.his.android.Model.UserDetail;

public class LoginResp {

    @SerializedName("userDetails")
    @Expose
    public List<UserDetail> userDetails = null;
    @SerializedName("headAssign")
    @Expose
    public List<HeadAssign> headAssign = null;
    @SerializedName("opdDays")
    @Expose
    public List<OpdDay> opdDays = null;

    public List<UserDetail> getUserDetails() {
        return userDetails;
    }

    public List<HeadAssign> getHeadAssign() {
        return headAssign;
    }

    public List<OpdDay> getOpdDays() {
        return opdDays;
    }
}