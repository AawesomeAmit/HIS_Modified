package com.trueform.era.his.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.HeadAssign;
import com.trueform.era.his.Model.OpdDay;
import com.trueform.era.his.Model.UserDetail;

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