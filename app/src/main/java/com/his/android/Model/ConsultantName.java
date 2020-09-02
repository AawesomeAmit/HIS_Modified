package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultantName {
    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("drID")
    @Expose
    public Integer drID;
    @SerializedName("drName")
    @Expose
    public String drName;
    @SerializedName("desigid")
    @Expose
    public Integer desigid;

    @Override
    public String toString() {
        return  drName;
    }

    public ConsultantName(Integer userid, Integer drID, String drName, Integer desigid) {
        this.userid = userid;
        this.drID = drID;
        this.drName = drName;
        this.desigid = desigid;
    }

    public Integer getUserid() {
        return userid;
    }

    public Integer getDrID() {
        return drID;
    }

    public String getDrName() {
        return drName;
    }

    public Integer getDesigid() {
        return desigid;
    }
}
