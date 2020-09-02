package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.CalculatorList;

import java.util.List;

public class GetCalculatorListResp {
    @SerializedName("calculatorList")
    @Expose
    public List<CalculatorList> calculatorList = null;

    public List<CalculatorList> getCalculatorList() {
        return calculatorList;
    }
}
