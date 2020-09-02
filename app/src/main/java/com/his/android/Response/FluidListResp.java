package com.his.android.Response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.IntakeMaster;
import com.his.android.Model.OutputList;
import com.his.android.Model.UnitMaster;

import java.util.List;

public class FluidListResp {
    @SerializedName("intakeList")
    @Expose
    public List<IntakeMaster> intakeMaster = null;
    @SerializedName("outputList")
    @Expose
    public List<OutputList> outputList = null;
    @SerializedName("unitMaster")
    @Expose
    public List<UnitMaster> unitMaster = null;

    public List<IntakeMaster> getIntakeMaster() {
        return intakeMaster;
    }

    public List<UnitMaster> getUnitMaster() {
        return unitMaster;
    }

    public List<OutputList> getOutputList() {
        return outputList;
    }
}
