package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class sendotpres {

    @SerializedName("otp")
    @Expose
    private List<Otp> otp = null;

    @SerializedName("table1")
    @Expose
    private List<OtpMobileNumber> table1 = null;

    @Override
    public String toString() {
        return "sendotpres{" +
                "otp=" + otp +
                ", table1=" + table1 +
                '}';
    }

    public List<OtpMobileNumber> getTable1() {
        return table1;
    }

    public void setTable1(List<OtpMobileNumber> table1) {
        this.table1 = table1;
    }

    public List<Otp> getOtp() {
        return otp;
    }

    public void setOtp(List<Otp> otp) {
        this.otp = otp;
    }


}