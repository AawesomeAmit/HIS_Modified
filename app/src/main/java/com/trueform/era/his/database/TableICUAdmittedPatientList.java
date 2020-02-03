package com.trueform.era.his.database;


public class TableICUAdmittedPatientList {
    public static final String icu_admitted_patient_list = "icu_admitted_patient_list";

    static final String SQL_CREATE_ICU_ADMITTED_PATIENT_LIST = ("CREATE TABLE icu_admitted_patient_list (" +
            icuPatientColumn.headId_subDeptId                       + " VARCHAR,"+
            icuPatientColumn.pmid                       + " VARCHAR,"+
            icuPatientColumn.wardID                   + " VARCHAR," +
            icuPatientColumn.previousWardID              + " VARCHAR," +
            icuPatientColumn.bedName         + " VARCHAR," +
            icuPatientColumn.pid             + " VARCHAR," +
            icuPatientColumn.crNo + " VARCHAR," +
            icuPatientColumn.ipNo + " VARCHAR," +
            icuPatientColumn.userID + " VARCHAR," +
            icuPatientColumn.subDeptID + " VARCHAR," +
            icuPatientColumn.subDepartmentName + " VARCHAR," +
            icuPatientColumn.status + " VARCHAR," +
            icuPatientColumn.consultantName + " VARCHAR," +
            icuPatientColumn.pname + " VARCHAR," +
            icuPatientColumn.sex + " VARCHAR," +
            icuPatientColumn.gender + " VARCHAR," +
            icuPatientColumn.age + " VARCHAR," +
            icuPatientColumn.ageUnit + " VARCHAR," +
            icuPatientColumn.address + " VARCHAR," +
            icuPatientColumn.fatherName + " VARCHAR," +
            icuPatientColumn.phoneNo + " VARCHAR," +
            icuPatientColumn.relation + " VARCHAR," +
            icuPatientColumn.admitDate + " VARCHAR," +
            icuPatientColumn.dischargeDate + " VARCHAR," +
            icuPatientColumn.ddate + " VARCHAR," +
            icuPatientColumn.dischargeStatus + " VARCHAR," +
            icuPatientColumn.previousWardName + " VARCHAR," +
            icuPatientColumn.notificationCount + " VARCHAR" + ")");

    public enum icuPatientColumn {
        headId_subDeptId,
        pmid,
        wardID,
        previousWardID,
        bedName,
        pid,
        crNo,
        ipNo,
        userID,
        subDeptID,
        subDepartmentName,
        status,
        consultantName,
        pname,
        sex,
        gender,
        age,
        ageUnit,
        address,
        fatherName,
        phoneNo,
        relation,
        admitDate,
        dischargeDate,
        ddate,
        dischargeStatus,
        previousWardName,
        notificationCount,
    }
}
