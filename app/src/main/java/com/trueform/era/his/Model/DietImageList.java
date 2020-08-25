package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DietImageList {
    @SerializedName("imageDietTime")
    @Expose
    public String imageDietTime;
    @SerializedName("patientDietImage")
    @Expose
    public String patientDietImage;

    public String getImageDietTime() {
        return imageDietTime;
    }

    public String getPatientDietImage() {
        return patientDietImage;
    }
}
