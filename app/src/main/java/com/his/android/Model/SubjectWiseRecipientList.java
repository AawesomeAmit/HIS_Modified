package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectWiseRecipientList {
    @SerializedName("recipientName")
    @Expose
    public String recipientName;

    public String getRecipientName() {
        return recipientName;
    }
}
