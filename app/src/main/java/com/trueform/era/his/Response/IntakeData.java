package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeData {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("intakeID")
    @Expose
    public Integer intakeID;
    @SerializedName("intakeDateTime")
    @Expose
    public String intakeDateTime;
    @SerializedName("intakeDate")
    @Expose
    public String intakeDate;
    @SerializedName("intakeTime")
    @Expose
    public String intakeTime;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("intakeQuantity")
    @Expose
    public Double intakeQuantity;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;
    @SerializedName("givenQuantity")
    @Expose
    public Double givenQuantity;
    @SerializedName("consumptionPercentage")
    @Expose
    public Integer consumptionPercentage;
    @SerializedName("entryUserID")
    @Expose
    public Integer entryUserID;
    @SerializedName("entryBy")
    @Expose
    public String entryBy;
    @SerializedName("isSupplement")
    @Expose
    public Integer isSupplement;
    @SerializedName("isEditing")
    @Expose
    public Integer isEditing;
    @SerializedName("isRemove")
    @Expose
    public Boolean isRemove;
    @SerializedName("isGiven")
    @Expose
    public Integer isGiven;

    public Integer getId() {
        return id;
    }

    public Integer getIntakeID() {
        return intakeID;
    }

    public String getIntakeDateTime() {
        return intakeDateTime;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public String getIntakeTime() {
        return intakeTime;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public Double getIntakeQuantity() {
        return intakeQuantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public Double getGivenQuantity() {
        return givenQuantity;
    }

    public Integer getConsumptionPercentage() {
        return consumptionPercentage;
    }

    public Integer getEntryUserID() {
        return entryUserID;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public Integer getIsSupplement() {
        return isSupplement;
    }

    public Integer getIsEditing() {
        return isEditing;
    }

    public Boolean getRemove() {
        return isRemove;
    }

    public Integer getIsGiven() {
        return isGiven;
    }
}
