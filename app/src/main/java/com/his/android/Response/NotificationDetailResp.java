package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationDetailResp {
    @SerializedName("pid")
    @Expose
    public Integer pid;
    @SerializedName("patientName")
    @Expose
    public String patientName;
    @SerializedName("patientNote")
    @Expose
    public String patientNote;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;
    @SerializedName("entryDateTime")
    @Expose
    public String entryDateTime;

    public Integer getPid() {
        return pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getEntryDateTime() {
        return entryDateTime;
    }
}
