package com.his.android.Model;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dosages {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @NonNull
    @Override
    public String toString(){
        return name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
