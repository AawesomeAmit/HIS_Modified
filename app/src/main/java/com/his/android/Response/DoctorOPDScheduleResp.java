package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.CardiologyVisitRevisitAlert;
import com.his.android.Model.DoctorOPDSchedule;

import java.util.List;

public class DoctorOPDScheduleResp {
    @SerializedName("doctorOPDSchedule")
    @Expose
    public List<DoctorOPDSchedule> doctorOPDSchedule = null;
    @SerializedName("cardiologyVisitRevisitAlert")
    @Expose
    public List<CardiologyVisitRevisitAlert> cardiologyVisitRevisitAlert = null;

    public List<DoctorOPDSchedule> getDoctorOPDSchedule() {
        return doctorOPDSchedule;
    }

    public List<CardiologyVisitRevisitAlert> getCardiologyVisitRevisitAlert() {
        return cardiologyVisitRevisitAlert;
    }
}
