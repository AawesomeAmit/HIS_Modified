package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientVitalGraph;

import java.util.List;

public class ObservationGraphResp {
    @SerializedName("patientVitalGraph")
    @Expose
    public List<PatientVitalGraph> patientVitalGraph = null;

    public void setPatientVitalGraph(List<PatientVitalGraph> patientVitalGraph) {
        this.patientVitalGraph = patientVitalGraph;
    }

    public List<PatientVitalGraph> getPatientVitalGraph() {
        return patientVitalGraph;
    }
}
