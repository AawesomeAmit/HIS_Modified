package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestListAngio {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("itemName")
    @Expose
    public String itemName;

    @Override
    public String toString() {
        return itemName;
    }

    public TestListAngio(Integer id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public Integer getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }
}
