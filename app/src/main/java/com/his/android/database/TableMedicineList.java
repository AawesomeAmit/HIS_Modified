package com.his.android.database;


public class TableMedicineList {
    public static final String icd_list = "icd_list";

    static final String SQL_CREATE_ICD_LIST = ("CREATE TABLE icd_list (" +
            icdListColumn.icdId + " VARCHAR,"
            + icdListColumn.icdCode + " VARCHAR" + ")");

    public enum icdListColumn {
        icdId,
        icdCode
    }
}
