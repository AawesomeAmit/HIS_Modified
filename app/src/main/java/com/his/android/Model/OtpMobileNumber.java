package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpMobileNumber {

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @Override
    public String toString() {
        return "OtpMobileNumber{" +
                "mobileNo='" + mobileNo + '\'' +
                '}';
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
