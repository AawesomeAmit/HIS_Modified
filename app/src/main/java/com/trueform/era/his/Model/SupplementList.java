package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplementList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("supplementName")
    @Expose
    public String supplementName;
    @SerializedName("medicineID")
    @Expose
    public Integer medicineID;
    @SerializedName("medicineBrandID")
    @Expose
    public Integer medicineBrandID;
    @SerializedName("medicineDose")
    @Expose
    public Float medicineDose;
    @SerializedName("doseUnitID")
    @Expose
    public Integer doseUnitID;
    @SerializedName("unitName")
    @Expose
    public String unitName;

    @Override
    public String toString() {
        return supplementName;
    }

    public Integer getId() {
        return id;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public Integer getMedicineID() {
        return medicineID;
    }

    public Integer getMedicineBrandID() {
        return medicineBrandID;
    }

    public Float getMedicineDose() {
        return medicineDose;
    }

    public Integer getDoseUnitID() {
        return doseUnitID;
    }

    public String getUnitName() {
        return unitName;
    }
}
