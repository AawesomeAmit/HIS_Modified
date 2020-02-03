package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.MedicineSearch;

import java.util.List;

public class MedicineSearchResp {
    @SerializedName("table")
    @Expose
    public List<MedicineSearch> medicineSearches = null;

    public void setMedicineSearches(List<MedicineSearch> medicineSearches) {
        this.medicineSearches = medicineSearches;
    }

    public List<MedicineSearch> getMedicineSearches() {
        return medicineSearches;
    }
}
