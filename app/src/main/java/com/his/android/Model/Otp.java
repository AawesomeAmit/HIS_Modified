package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp {

    @SerializedName("currentOTP")
    @Expose
    private String currentOTP;

    @Override
    public String toString() {
        return "Otp{" +
                "currentOTP='" + currentOTP + '\'' +
                '}';
    }

    public String getCurrentOTP() {
        return currentOTP;
    }

    public void setCurrentOTP(String currentOTP) {
        this.currentOTP = currentOTP;
    }


}