package com.his.android.database;


public class TablePatientVitalList {
    public static final String patient_vital_list = "patient_vital_list";

    static final String SQL_CREATE_PATIENT_VITAL_LIST = ("CREATE TABLE patient_vital_list (" +
            patientVitalListColumn.pId_headId + " VARCHAR,"
            + patientVitalListColumn.vitalName + " VARCHAR,"
            + patientVitalListColumn.vmID + " VARCHAR,"
            + patientVitalListColumn.vitalValue + " VARCHAR,"
            + patientVitalListColumn.vitalUnit + " VARCHAR" + ")");

    public enum patientVitalListColumn {
        pId_headId,
        vitalName,
        vmID,
        vitalValue,
        vitalUnit
    }
}
