package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.CalculatorList;

import java.util.List;

public class GetCalculatorListResp {
    @SerializedName("calculatorList")
    @Expose
    public List<CalculatorList> calculatorList = null;

    public List<CalculatorList> getCalculatorList() {
        return calculatorList;
    }
}
