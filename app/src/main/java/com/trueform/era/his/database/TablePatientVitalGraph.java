package com.trueform.era.his.database;


public class TablePatientVitalGraph {
    public static final String patient_vital_graph = "patient_vital_graph";

    static final String SQL_CREATE_PATIENT_VITAL_GRAPH = ("CREATE TABLE patient_vital_graph (" +
            patientVitalGraphColumn.pId_headId + " VARCHAR,"
            + patientVitalGraphColumn.pulse + " VARCHAR,"
            + patientVitalGraphColumn.bp_sys + " VARCHAR,"
            + patientVitalGraphColumn.bp_dias + " VARCHAR,"
            + patientVitalGraphColumn.respRate + " VARCHAR,"
            + patientVitalGraphColumn.spo2 + " VARCHAR,"
            + patientVitalGraphColumn.hr + " VARCHAR,"
            + patientVitalGraphColumn.x + " VARCHAR,"
            + patientVitalGraphColumn.createdDate + " VARCHAR,"
            + patientVitalGraphColumn.heartRate + " VARCHAR" + ")");

    public enum patientVitalGraphColumn {
        pId_headId,
        pulse,
        bp_sys,
        bp_dias,
        respRate,
        spo2,
        heartRate,
        hr,
        createdDate,
        x
    }
}
