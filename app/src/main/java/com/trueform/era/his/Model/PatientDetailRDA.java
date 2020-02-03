package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetailRDA {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("ageUnit")
    @Expose
    public String ageUnit;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("symptomName")
    @Expose
    private String symptomName;
    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("rdaPercentage")
    @Expose
    public Integer rdaPercentage;
    @SerializedName("originalRDA")
    @Expose
    public Float originalRDA;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("adviceType")
    @Expose
    public Integer adviceType;
    @SerializedName("advice")
    @Expose
    public String advice;

    public String getSymptomName() {
        return symptomName;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public Integer getAdviceType() {
        return adviceType;
    }

    public String getAdvice() {
        return advice;
    }

    public Float getOriginalRDA() {
        return originalRDA;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public String getGender() {
        return gender;
    }

    public Integer getRdaPercentage() {
        return rdaPercentage;
    }
}
