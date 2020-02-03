package com.trueform.era.his.Response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.IntakeMaster;
import com.trueform.era.his.Model.OutputList;
import com.trueform.era.his.Model.UnitMaster;

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
