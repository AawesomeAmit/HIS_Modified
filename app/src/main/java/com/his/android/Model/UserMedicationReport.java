package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMedicationReport {
    @SerializedName("medicineName")
    @Expose
    public String medicineName;
    @SerializedName("sideEffect")
    @Expose
    public String sideEffect;
    @SerializedName("medicineInteraction")
    @Expose
    public String medicineInteraction;
    @SerializedName("medicinePathway")
    @Expose
    public String medicinePathway;
    @SerializedName("mechanismOfAction")
    @Expose
    public String mechanismOfAction;

    public String getMedicineName() {
        return medicineName;
    }

    public String getMechanismOfAction() {
        return mechanismOfAction;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public String getMedicineInteraction() {
        return medicineInteraction;
    }

    public String getMedicinePathway() {
        return medicinePathway;
    }
}
