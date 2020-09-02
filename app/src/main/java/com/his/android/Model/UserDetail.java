package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("desigid")
    @Expose
    private Integer desigid;
    @SerializedName("userTypeID")
    @Expose
    private Integer userTypeID;

    public UserDetail(Integer userid, String username, String displayName, String accessToken, Integer desigid, Integer userTypeID) {
        this.userid = userid;
        this.username = username;
        this.displayName = displayName;
        this.accessToken = accessToken;
        this.desigid = desigid;
        this.userTypeID = userTypeID;
    }

    public Integer getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getDesigid() {
        return desigid;
    }

    public Integer getUserTypeID() {
        return userTypeID;
    }
}