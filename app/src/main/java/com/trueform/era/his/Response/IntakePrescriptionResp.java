package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.IntakeList;
import com.trueform.era.his.Model.UniqueDate;

import java.util.List;

public class IntakePrescriptionResp {
    @SerializedName("intakeList")
    @Expose
    public List<IntakeList> intakeList = null;

    public List<IntakeList> getIntakeList() {
        return intakeList;
    }

    public List<UniqueDate> getUniqueDate() {
        return uniqueDate;
    }

    @SerializedName("uniqueDate")
    @Expose
    public List<UniqueDate> uniqueDate = null;
}
