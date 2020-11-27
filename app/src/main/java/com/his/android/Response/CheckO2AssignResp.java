package com.his.android.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.CurrentOxygenValue;
import com.his.android.Model.LifeSupport;
import com.his.android.Model.PatientIsOnOxygen;
import com.his.android.Model.PatientIsOnVentilator;

public class CheckO2AssignResp {
    @SerializedName("patientIsOnOxygen")
    @Expose
    public List<PatientIsOnOxygen> patientIsOnOxygen = null;
    @SerializedName("currentOxygenValue")
    @Expose
    public List<CurrentOxygenValue> currentOxygenValue = null;
    @SerializedName("lifeSupport")
    @Expose
    public List<LifeSupport> lifeSupport = null;
    @SerializedName("patientIsOnVentilator")
    @Expose
    public List<PatientIsOnVentilator> patientIsOnVentilator = null;

    public List<PatientIsOnOxygen> getPatientIsOnOxygen() {
        return patientIsOnOxygen;
    }

    public List<CurrentOxygenValue> getCurrentOxygenValue() {
        return currentOxygenValue;
    }

    public List<Object> getLifeSupport() {
        return lifeSupport;
    }

    public List<PatientIsOnVentilator> getPatientIsOnVentilator() {
        return patientIsOnVentilator;
    }
}
