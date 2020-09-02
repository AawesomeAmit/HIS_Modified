package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicalNotification {
    @SerializedName("input")
    @Expose
    public String input;
    @SerializedName("toDo")
    @Expose
    public String toDo;
    @SerializedName("notToDo")
    @Expose
    public String notToDo;

    public String getInput() {
        return input;
    }

    public String getToDo() {
        return toDo;
    }

    public String getNotToDo() {
        return notToDo;
    }
}
