package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DiagnosisMaster;
import com.trueform.era.his.Model.TypeMaster;
import com.trueform.era.his.Model.UnitMaster;
import com.trueform.era.his.Model.VitalMaster;

import java.util.List;

public class RangeMasterResp {
    @SerializedName("diagnosisMaster")
    @Expose
    public List<DiagnosisMaster> diagnosisMaster = null;
    @SerializedName("typeMaster")
    @Expose
    public List<TypeMaster> typeMaster = null;
    @SerializedName("unitMaster")
    @Expose
    public List<UnitMaster> unitMaster = null;
    @SerializedName("vitalMaster")
    @Expose
    public List<VitalMaster> vitalMaster = null;

    public List<DiagnosisMaster> getDiagnosisMaster() {
        return diagnosisMaster;
    }

    public List<TypeMaster> getTypeMaster() {
        return typeMaster;
    }

    public List<UnitMaster> getUnitMaster() {
        return unitMaster;
    }

    public List<VitalMaster> getVitalMaster() {
        return vitalMaster;
    }
}
