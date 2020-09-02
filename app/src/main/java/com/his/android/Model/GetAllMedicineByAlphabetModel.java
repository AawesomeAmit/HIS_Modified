package com.his.android.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllMedicineByAlphabetModel {

    private boolean isSelected = false;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("medicineName")
    @Expose
    private String medicineName;
    @SerializedName("medicineBrand")
    @Expose
    private String medicineBrand;

    public void setMedicineBrand(String medicineBrand) {
        this.medicineBrand = medicineBrand;
    }

    public Integer getId() {
        return id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineBrand() {
        return medicineBrand;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @NonNull
    @Override
    public String toString()
    {
        return medicineName.trim();
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}