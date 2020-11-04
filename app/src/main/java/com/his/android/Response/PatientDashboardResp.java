package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.Model.VitalDetail;
import com.his.android.Model.PatientDetailDashboard;
import com.his.android.Model.PatientActivityDetail;
import com.his.android.Model.MedicineDetail;
import com.his.android.Model.IntakeDetail;

public class PatientDashboardResp {
    @SerializedName("patientDetails")
    @Expose
    public List<PatientDetailDashboard> patientDetails = null;
    @SerializedName("vitalDetails")
    @Expose
    public List<VitalDetail> vitalDetails = null;
    @SerializedName("patientActivityDetails")
    @Expose
    public List<PatientActivityDetail> patientActivityDetails = null;
    @SerializedName("medicineDetails")
    @Expose
    public List<MedicineDetail> medicineDetails = null;
    @SerializedName("intakeDetails")
    @Expose
    public List<IntakeDetail> intakeDetails = null;

    public List<PatientDetailDashboard> getPatientDetails() {
        return patientDetails;
    }

    public List<VitalDetail> getVitalDetails() {
        return vitalDetails;
    }

    public List<PatientActivityDetail> getPatientActivityDetails() {
        return patientActivityDetails;
    }

    public List<MedicineDetail> getMedicineDetails() {
        return medicineDetails;
    }

    public List<IntakeDetail> getIntakeDetails() {
        return intakeDetails;
    }
}
