package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientTest {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("billID")
    @Expose
    public Integer billID;
    @SerializedName("sampleCollectionMainID")
    @Expose
    public Integer sampleCollectionMainID;
    @SerializedName("itemID")
    @Expose
    public Integer itemID;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("displayOrder")
    @Expose
    public Object displayOrder;
    @SerializedName("categoryID")
    @Expose
    public Integer categoryID;
    @SerializedName("subTest")
    @Expose
    public String subTest;

    public PatientTest(Integer id, Integer billID, Integer sampleCollectionMainID, Integer itemID, String itemName, Object displayOrder, Integer categoryID, String subTest) {
        this.id = id;
        this.billID = billID;
        this.sampleCollectionMainID = sampleCollectionMainID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.displayOrder = displayOrder;
        this.categoryID = categoryID;
        this.subTest = subTest;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBillID() {
        return billID;
    }

    public Integer getSampleCollectionMainID() {
        return sampleCollectionMainID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public Object getDisplayOrder() {
        return displayOrder;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getSubTest() {
        return subTest;
    }
}
