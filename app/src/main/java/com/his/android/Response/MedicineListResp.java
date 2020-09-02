package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.MedicineList;

import java.util.List;

public class MedicineListResp {
    @SerializedName("medicineList")
    @Expose
    public List<MedicineList> medicineList = null;

    public List<MedicineList> getMedicineList() {
        return medicineList;
    }
}
