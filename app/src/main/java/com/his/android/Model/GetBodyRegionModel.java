package com.his.android.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBodyRegionModel {
    private boolean isSelected = false;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("regionName")
    @Expose
    private String regionName;
    @SerializedName("organImagePath")
    @Expose
    private String organImagePath;

    public Integer getId() {
        return id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getOrganImagePath() {
        return organImagePath;
    }

    public void setOrganImagePath(String organImagePath) {
        this.organImagePath = organImagePath;
    }

    @NonNull
    @Override
    public String toString() {
        return regionName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
