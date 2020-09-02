package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Dosages;
import com.his.android.Model.DrugUnit;
import com.his.android.Model.Frequency;
import java.util.List;

public class GetFreqResponse {
    @SerializedName("frequency")
    @Expose
    public List<Frequency> frequency = null;
    @SerializedName("dosages")
    @Expose
    public List<Dosages> dosages = null;
    @SerializedName("drugUnit")
    @Expose
    public List<DrugUnit> drugUnit = null;

    public List<Frequency> getFrequency() {
        return frequency;
    }

    public List<Dosages> getDosages() {
        return dosages;
    }

    public List<DrugUnit> getDrugUnit() {
        return drugUnit;
    }
}
