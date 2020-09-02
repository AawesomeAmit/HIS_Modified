package com.his.android.database;


public class TableSubDeptList {
    public static final String sub_dept_list = "sub_dept_list";

    static final String SQL_CREATE_SUB_DEPT_LIST = ("CREATE TABLE sub_dept_list (" +
            subDeptListColumn.id + " VARCHAR,"
            + subDeptListColumn.bgColor + " VARCHAR,"
            + subDeptListColumn.headID + " VARCHAR,"
            + subDeptListColumn.subDepartmentName + " VARCHAR" + ")");

    public enum subDeptListColumn {
        id,
        bgColor,
        headID,
        subDepartmentName
    }
}
