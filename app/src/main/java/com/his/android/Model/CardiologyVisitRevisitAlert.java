package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardiologyVisitRevisitAlert {
    @SerializedName("cardiologyVisitRevisit")
    @Expose
    public String cardiologyVisitRevisit;

    public String getCardiologyVisitRevisit() {
        return cardiologyVisitRevisit;
    }
}
