package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodTiming {
    @SerializedName("foodTimeID")
    @Expose
    public Integer foodTimeID;
    @SerializedName("foodTime")
    @Expose
    public String foodTime;
    @SerializedName("foodTiming")
    @Expose
    public String foodTiming;
    public boolean isClicked=false;

    public Integer getFoodTimeID() {
        return foodTimeID;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public String getFoodTiming() {
        return foodTiming;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
