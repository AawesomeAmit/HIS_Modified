package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GetIdealNutrientGraphDetailModel;
import com.his.android.Model.GetIdealNutrientIntakeDetailModel;
import com.his.android.Model.GetIdealNutrientRdaDetailModel;

import java.util.List;

public class GetIdealNutrientIntakeDataResp {
    @SerializedName("RDADetails")
    @Expose
    public List<GetIdealNutrientRdaDetailModel> rDADetails = null;
    @SerializedName("IntakeDetails")
    @Expose
    public List<GetIdealNutrientIntakeDetailModel> intakeDetails = null;
    @SerializedName("GraphDetails")
    @Expose
    public List<GetIdealNutrientGraphDetailModel> graphDetails = null;

    public List<GetIdealNutrientRdaDetailModel> getrDADetails() {
        return rDADetails;
    }

    public List<GetIdealNutrientIntakeDetailModel> getIntakeDetails() {
        return intakeDetails;
    }

    public List<GetIdealNutrientGraphDetailModel> getGraphDetails() {
        return graphDetails;
    }
}
