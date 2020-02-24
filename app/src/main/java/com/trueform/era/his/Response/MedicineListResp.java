package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.MedicineList;

import java.util.List;

public class MedicineListResp {
    @SerializedName("medicineList")
    @Expose
    public List<MedicineList> medicineList = null;

    public List<MedicineList> getMedicineList() {
        return medicineList;
    }
}
